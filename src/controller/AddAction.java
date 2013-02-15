public class AddAction extends AbstractAction {
	public AddAction(String text, ImageIcon icon
					 String desc, Integer mnemonic) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
	}
}