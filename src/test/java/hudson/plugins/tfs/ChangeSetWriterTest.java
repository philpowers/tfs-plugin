package hudson.plugins.tfs;

import static org.custommonkey.xmlunit.XMLAssert.*;
import java.io.StringWriter;
import java.util.ArrayList;

import hudson.plugins.tfs.model.TeamFoundationChangeSet;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

public class ChangeSetWriterTest {

    @Before
    public void setUp() {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);
        XMLUnit.setIgnoreComments(true);
    }

    @Test
    public void assertWriterOutputsCorrectChangeLogXml() throws Exception {
        TeamFoundationChangeSet changeset = new TeamFoundationChangeSet("1122", Util.getCalendar(2008, 12, 12).getTime(), "rnd\\user", "comment");
        changeset.getItems().add(new TeamFoundationChangeSet.Item("path", "add"));
        changeset.getItems().add(new TeamFoundationChangeSet.Item("path2", "delete"));
        ArrayList<TeamFoundationChangeSet> sets = new ArrayList<TeamFoundationChangeSet>();
        sets.add(changeset);

        ChangeSetWriter changesetWriter = new ChangeSetWriter();
        StringWriter output = new StringWriter();
        changesetWriter.write(sets, output);
        assertXMLEqual("<?xml version=\"1.0\" encoding=\"UTF-8\"?><changelog>" +
                            "<changeset version=\"1122\">" +
                                "<date>2008-12-12T00:00:00Z</date>" +
                                "<user>rnd\\user</user>" +
                                "<comment>comment</comment>" +
                                "<items>" +
                                    "<item action=\"add\">path</item>" +
                                    "<item action=\"delete\">path2</item>" +
                                "</items>" +
                            "</changeset>" +
        		"</changelog>", output.getBuffer().toString());
    }
}