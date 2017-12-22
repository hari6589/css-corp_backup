Steps for configuring and deploying the war file to websphere app server are:

1. First Enable the checkbox for Global deployment settings

websphear admin screen --> Applications --> Global deployment settings --> Enable the checkbox - "Monitor directory to automatically deploy applications"
--> Apply

3. It will create a folder having a name  "monitoredDeployableApps" inside --\Program Files (x86)\IBM\WebSphere\AppServer\profiles\AppSrv01

4. Use the files : workstation.properties, buildAntSite.xml, buildDependencies.xml
Note: for local build modify the buildDependencies.xml file. below mentioned change in the line number: 307
<fileset dir="${root.master.working.dir}/${bsro.libraries.jars.dir}" includes="${jar.dependencies.path}" />
<fileset dir="${root.master.working.dir}/BSRO/${bsro.libraries.jars.dir}" includes="${jar.dependencies.path}" />

5. Remove the ibm-web-bnd.xmi and ibm-web-ext.xmi files from \WEB-INF\.

6. Take build(Run As --> Ant Build)

7.Script will copy the generated war file to server location(\\Program Files (x86)\\IBM\\WebSphere\\AppServer\\profiles\\AppSrv01\\monitoredDeployableApps\\servers\\server1)

8. Server will picks up this war file and starts the application.
