import java.awt.Image;

import turban.utils.IGuifiable;

public enum HSPersonellType  implements IGuifiable {
	Student("Student",true,false,false,false),
	Professor("Professor",false,true,false,false),
	Tutor("Tutor",false,true,false,false),
	President("President",false,true,true,false),
	Dean("Dean",false,true,true,false),
	ITAdmin("ITAdmin",false,false,false,true),
	Secretary("Secretary",false,false,false,true)
	;
	private String _name;
	private boolean _receivesLessons;
	private boolean _givesLessons;
	private boolean _hasOrgResp;
	private boolean _isAdminStaff;
	
	private HSPersonellType(String name,boolean receivesLessons,boolean givesLessons
			, boolean hasOrgResp, boolean isAdminStaff) {
		
		this._name=name;
		this._receivesLessons=receivesLessons;
		this._givesLessons=givesLessons;
		this._hasOrgResp=hasOrgResp;
		this._isAdminStaff=isAdminStaff;
		}
	@Override
	public String toGuiString() {
		return this._name;
	}
	@Override
	public Image getGuiIcon() {
		return null;
	}
	public boolean receivesLessons() {
		return this._receivesLessons;
	}
	public boolean givesLessons () {
		return this._givesLessons;
	}
	public boolean hasOrgResp () {
		return this._hasOrgResp;
	}
	public boolean isAdminStaff () {
		return this._isAdminStaff;
	}
	/**
	 * 
	 */
	public void printEnumValues() {
		HSPersonellType [] lst= HSPersonellType.values();
		
		for(HSPersonellType values : lst) {
			System.out.println(values);
		}
	}
	public static void main(String[] args) {
		HSPersonellType hs=HSPersonellType.Dean;
		hs.printEnumValues();
	}
	

}
