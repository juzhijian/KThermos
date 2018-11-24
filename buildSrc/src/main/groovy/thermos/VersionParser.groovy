package thermos

import java.util.regex.Matcher

class VersionParser {
    static String parseForgeVersion(File forgeFile, File propsFile) {
        def forgeVersion = forgeFile.text
        int majorVersion = v(forgeVersion =~ /.+int majorVersion\s+=\s+(\d+);/)
        int minorVersion = v(forgeVersion =~ /.+int minorVersion\s+=\s+(\d+);/)
        int revisionVersion = v(forgeVersion =~ /.+int revisionVersion\s+=\s+(\d+);/)
        int buildVersion = parseForgeRevision propsFile
        return "${majorVersion}.${minorVersion}.${revisionVersion}.${buildVersion}"
    }

    static int parseForgeRevision(File propsFile) {
        def props = new Properties()
        propsFile.withInputStream { props.load(it) }
        props['fmlbuild.build.number'] as int
    }


    static int v(Matcher matcher) {
        matcher.find()
        matcher.group(1) as int
    }
}
