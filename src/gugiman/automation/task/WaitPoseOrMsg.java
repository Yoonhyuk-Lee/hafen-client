package gugiman.automation.task;

import haven.GameUI;
import haven.Gob;
import haven.UI;

public class WaitPoseOrMsg implements Task
{
    private Gob gob;
    private String pose;
    private String msg;
    private boolean isError = false;
    private int count = 0;

    public WaitPoseOrMsg(Gob gob, String pose, String msg)
    {
	this.gob 	= gob;
	this.pose 	= pose;
	this.msg 	= msg;
    }

    @Override
    public boolean check()
    {
	count++;
	if(gob.hasPose(pose))
	    return true;

	String lastMsg = haven.UI.getInstance().getLastError();
	if((lastMsg != null && lastMsg.toLowerCase().contains(msg.toLowerCase())) || count > 300)
	{
	    UI.getInstance().message("Error", GameUI.MsgType.ERROR);
	    isError = true;
	    return true;
	}
	return false;
    }

    public boolean isError() {
	return isError;
    }
}
