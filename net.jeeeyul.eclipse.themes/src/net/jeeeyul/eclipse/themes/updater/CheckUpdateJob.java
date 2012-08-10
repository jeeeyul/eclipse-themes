package net.jeeeyul.eclipse.themes.updater;

import java.net.URI;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.IProvisioningAgentProvider;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class CheckUpdateJob extends Job {

	public CheckUpdateJob() {
		super("Check update for Jeeeyul's eclipse 4 themes");
		setSystem(true);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			BundleContext context = ChromeThemeCore.getDefault().getBundle().getBundleContext();

			// P2 에이전트를 얻음
			ServiceReference<IProvisioningAgentProvider> sr = context.getServiceReference(IProvisioningAgentProvider.class);
			IProvisioningAgentProvider agentProvider = context.getService(sr);

			URI p2InstanceURI = null;
			final IProvisioningAgent agent = agentProvider.createAgent(p2InstanceURI);

			// P2 프로필을 얻음
			IProfileRegistry regProfile = (IProfileRegistry) agent.getService(IProfileRegistry.SERVICE_NAME);
			IProfile profileSelf = regProfile.getProfile(IProfileRegistry.SELF);

			// 쿼리 생성 및, 오퍼레이션 생성
			IQuery<IInstallableUnit> query = QueryUtil.createIUQuery("업데이트할 피쳐 ID");
			IQueryResult<IInstallableUnit> result = profileSelf.query(query, new NullProgressMonitor());
			UpdateOperation operation = new UpdateOperation(ProvisioningUI.getDefaultUI().getSession(), result.toSet());

			// 업데이트 가능 여부 확인
			IStatus resolveResult = operation.resolveModal(new NullProgressMonitor());
			if (resolveResult.isOK()) {
				ChromeUpdateNotificationPopup popup = new ChromeUpdateNotificationPopup(Display.getDefault(), operation);
				popup.open();
			} else {
				System.out.println(resolveResult.getMessage());
			}
		} catch (Exception e) {
			return new Status(IStatus.WARNING, ChromeThemeCore.PLUGIN_ID, "Could not check new update.", e);
		}

		return Status.OK_STATUS;
	}

}
