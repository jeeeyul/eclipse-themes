package net.jeeeyul.eclipse.themes.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UpdateVersionTask extends Task {
	private String feature;
	private String version;

	@Override
	public void execute() throws BuildException {
		try {
			ArrayList<Manifest> manifests = new ArrayList<Manifest>();
			ArrayList<IProject> projects = getProjects();
			ArrayList<String> bundleIdList = new ArrayList<String>();
			for (IProject eachProject : projects) {
				if (eachProject.getName().contains("kr.or.eclipse")) {
					continue;
				}
				IFile manifestFile = eachProject.getFile(new Path(
						"META-INF/MANIFEST.MF"));
				Manifest m = new Manifest(manifestFile);
				manifests.add(m);
				bundleIdList.add(m.getBundleId());
			}

			for (Manifest m : manifests) {
				System.out.println(m.getBundleId() + " 버전 수정 > " + this.version
						+ ".qualifier");
				m.setBundleVersion(this.version + ".qualifier");
				m.updateDependencies(bundleIdList, this.version);
				m.save();
			}

			System.out.println("피쳐 파일의 버전을 수정하는 중...");
			IFile featureFile = getFeatureProject().getFile("feature.xml");
			String featureContent = read(featureFile.getContents(),
					featureFile.getCharset());
			String newcontent = featureContent.replaceAll(
					"version=\"[0-9]+\\.[0-9]+\\.[0-9]+\\.qualifier\"",
					"version=\"" + this.version + ".qualifier\"");
			write(featureFile, newcontent, featureFile.getCharset());

		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	public String getFeature() {
		return feature;
	}

	private IProject getFeatureProject() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject featureProject = root.getProject(this.feature);
		return featureProject;
	}

	public ArrayList<IProject> getProjects() {
		final ArrayList<IProject> result = new ArrayList<IProject>();
		try {
			IProject featureProject = getFeatureProject();
			IFile featureXMLFile = featureProject.getFile("feature.xml");
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if ("plugin".equals(qName)) {
						String id = attributes.getValue("id");
						IProject eachProject = ResourcesPlugin.getWorkspace()
								.getRoot().getProject(id);
						if (!eachProject.exists()) {
							throw new BuildException(
									MessageFormat
											.format("아이디가 {0}인 프로젝틀르 찾지 못했습니다. 프로젝트 아이디와 프로젝트 명은 동일한 것으로 간주합니다.",
													id));
						}
						result.add(eachProject);
					}
				}
			};
			parser.parse(featureXMLFile.getContents(), handler);
		} catch (Exception e) {
			throw new BuildException(e.getMessage(), e);
		}

		return result;
	}

	public String getVersion() {
		return version;
	}

	public String read(IFile file, String encoding) throws IOException,
			CoreException {
		String result = read(file.getContents(), encoding);
		return result;

	}

	public String read(InputStream stream, String encoding) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte buf[] = new byte[512];
		int len = -1;
		while ((len = stream.read(buf)) != -1) {
			buffer.write(buf, 0, len);
		}
		stream.close();
		buffer.close();
		String result = new String(buffer.toByteArray(), encoding);

		return result;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void write(IFile file, String content, String encoding)
			throws IOException, CoreException {
		byte[] data = content.getBytes(encoding);
		ByteArrayInputStream buffer = new ByteArrayInputStream(data);
		file.setContents(buffer, true, true, null);
	}

}
