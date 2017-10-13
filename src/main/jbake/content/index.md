title=Welcome to the Jiiify Image Project
date=2017-10-10
type=page
status=published
~~~~~~

This project is a Java library for working with the IIIF Image API. It's not a server, but provides components that could be used in a server or in a client-side (or command line) application. It's still in active development and shouldn't yet be considered stable.

<script>
xmlhttp=new XMLHttpRequest();
xmlhttp.open("GET", "https://55rkvqxzlf.execute-api.us-east-1.amazonaws.com/maven?q=jiiify-image", false);
xmlhttp.send();
$version = xmlhttp.responseText;
</script>

## Using Jiiify Image

To use the jiiify-image library, reference it in your project's `pom.xml` file.

<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;info.freelibrary&lt;/groupId&gt;
  &lt;artifactId&gt;jiiify-image&lt;/artifactId&gt;
  &lt;version&gt;<script>document.write($version);</script><noscript>${version}</noscript>&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

<br/>Or, to use it with Gradle/Grails, include the following in your project's `build.gradle` file:

<pre><code>compile &apos;info.freelibrary:jiiify-image:<script>
document.write($version);</script><noscript>${version}</noscript>&apos;</code></pre>
<p/>

## Building FreeLib-Utils

To build the project, you need a [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) environment with [Maven](http://maven.apache.org/) installed on your system.

    git clone https://github.com/ksclarke/jiiify-image.git
    cd jiiify-image

<br/>To build the project, type:

    mvn install

<br/>To build the project's documentation, type:

    mvn javadoc:javadoc

<br/>For more information, consult the "Docs" dropdown in the navigation menu at the top of this page.
