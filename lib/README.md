# Disclaimer
The entirety of this jar archive contains the mainly unchanged code from the
SDMetricsOpenCore v2.35 which is available for download [here](https://www
.sdmetrics.com/OpenCore.html). This link was last
accessed on december 20th 2019. The modified source is hosted in [this
 repository](https://github.com/sebastianBoehringer/SDMetricsOpenCore). 

The code is licensed under the [GNU AGPL v3.0](https://www.gnu.org/licenses/agpl-3.0.en.html) which you can read by
either following the provided link or reading the LICENSE.md in the directory root.

#Installing to Maven
The library is used as a dependency for the POM of this project. Since it is
 not distributed on Maven Central. This can be done by executing the
 following command in the root directory, i. e. the one with the pom.xml file
 directly in it, of the project:
`$MAVEN_HOME/mvn install:install-file -Dfile="lib\SDMetricsOpenCore-2.35-mod.jar" -DgroupId="com.SDMetrics" -DartifactId="open-core" -Dversion="2.35-mod" -Dpackaging="jar" `
where `$MAVEN_HOME` points to the bin folder of your Maven installation.