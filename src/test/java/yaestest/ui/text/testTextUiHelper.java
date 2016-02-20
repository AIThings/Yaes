package yaestest.ui.text;

import junit.framework.TestCase;
import yaes.ui.text.TextUi;
import yaes.ui.text.TextUiHelper;

public class testTextUiHelper extends TestCase {
    /*
     * Test method for 'yaes.ui.text.TextUiHelper.formatTimeInterval(long)'
     */
    public void testFormatTimeInterval() {
        long milliseconds = 10000;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
        milliseconds = milliseconds * 10;
        TextUi.println(TextUiHelper.formatTimeInterval(milliseconds));
    }
}
