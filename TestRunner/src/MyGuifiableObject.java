import java.awt.Image;

import turban.utils.IGuifiable;

public class MyGuifiableObject implements IGuifiable {
	String _guiString;
	
	public MyGuifiableObject (String guiString) {
		_guiString=guiString;
	}
	@Override
	public Image getGuiIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setGuiString(String guiString) {
		_guiString=guiString;
	}

	@Override
	public String toGuiString() {
		
		return this._guiString;
	}

}
