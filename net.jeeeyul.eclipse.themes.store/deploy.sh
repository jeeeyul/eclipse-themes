meteor build tarball
mv tarball/net.jeeeyul.eclipse.themes.store.tar.gz ~/Workspaces/jeeeyul\@openshift.redhat.com/themes/
cd ~/Workspaces/jeeeyul\@openshift.redhat.com/themes/
rm -rf programs server
rm -f main.js
tar -xvf net.jeeeyul.eclipse.themes.store.tar.gz -s '/^bundle//'
rm net.jeeeyul.eclipse.themes.store.tar.gz
git add --all
git commit -m "new publish"
git push
