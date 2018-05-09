# jiiify-image &nbsp;[![Build Status](https://travis-ci.org/ksclarke/jiiify-image.svg)](https://travis-ci.org/ksclarke/jiiify-image) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/39ce86dea4e94e969aba8b50a42cf7f8)](https://www.codacy.com/app/ksclarke/jiiify-image?utm_source=github.com&utm_medium=referral&utm_content=ksclarke/jiiify-image&utm_campaign=Badge_Coverage) [![Codacy grade](https://api.codacy.com/project/badge/Grade/39ce86dea4e94e969aba8b50a42cf7f8)](https://www.codacy.com/app/ksclarke/jiiify-image?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ksclarke/jiiify-image&amp;utm_campaign=Badge_Grade) [![Known Vulnerabilities](https://snyk.io/test/github/ksclarke/jiiify-image/badge.svg)](https://snyk.io/test/github/ksclarke/jiiify-image) [![Maven Central](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/info/freelibrary/jiiify-image/maven-metadata.xml.svg?colorB=brightgreen)](http://mvnrepository.com/artifact/info.freelibrary/jiiify-image) [![Javadocs](http://javadoc.io/badge/info.freelibrary/jiiify-image.svg)](http://projects.freelibrary.info/jiiify-image/javadocs.html)

A [IIIF Image API](http://iiif.io/api/image) library for Java. It does not contain a server. It's meant to be used in a server or it can be used from a command line / GUI application that generates images from files on disk or byte arrays.

This is a work in progress. Not everything is supported yet. In particular, right now it just supports JPEGs, PNGs, TIFs, and GIFs.

A very simple example:

    final Image image = ImageFactory.getImage(new File("src/test/resources/images/test.tif"));
    
    image.resizeTo(1000, 1000).write(Format.JPG, new File("tif-resized.jpg"));

A slightly more complicated example:

    final Image image = ImageFactory.getImage(new File("src/test/resources/images/test.tif"));
    final String id = UUID.randomUUID().toString();
    final Region region = new Region(0, 0, 1000, 1000);
    final Size size = new Size(500, 500);
    final Rotation rotation = new Rotation(45);
    final Request request = new Request(id, "/iiif", region, size, rotation, Quality.COLOR);

    image.transform(request).write(format.JPG, new File("tif-transformed.jpg"));

And another example:

    final Image image = ImageFactory.getImage(new File("src/test/resources/images/test.tif"));
    final Request request = Request.parse("/iiif/ASDF123/0,0,1000,1000/500,500/45/default.jpg");
    final Format format = request.getFormat();

    image.transform(request).write(format, new File("tif-transformed." + format.getExtension());

### Getting Started

To check out and build the project, type on the command line:

    git clone https://github.com/ksclarke/jiiify-image.git
    cd jiiify-image
    mvn install

This will put the jar in your local Maven repository and you can reference it from your project.

To build the Javadocs, from the command line, run: `mvn javadoc:javadoc`

### Contact

If you have questions about jiiify-image <a href="mailto:ksclarke@ksclarke.io">feel free to ask</a> or, if you encounter a problem, please feel free to [open an issue](https://github.com/ksclarke/jiiify-image/issues "GitHub Issue Queue") in the project's issue queue.
