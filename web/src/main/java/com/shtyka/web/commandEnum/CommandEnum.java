package com.shtyka.web.commandEnum;

import com.shtyka.web.command.*;

//a list of all commands
public enum CommandEnum {
	LOGIN {
		{
			this.command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	DELETE {
		{
			this.command = new DeleteCommand();
		}
	},
	ORDER {
		{
			this.command = new OrderCommand();
		}
	},
	CLEAR {
		{
			this.command = new ClearCommand();
		}
	},
	COOKING {
		{
			this.command = new CookingCommand();
		}
	},
	READY {
		{
			this.command = new ReadyCommand();
		}
	},
	COUNT {
		{
			this.command = new CountCommand();
		}
	},
	DISCOUNT {
		{
			this.command = new DiscountCommand();
		}
	},
	ALLOWANCE {
		{
			this.command = new AllowanceCommand();
		}
	},
	LANGUAGE {
		{
			this.command = new LanguageCommand();
		}
	},
	CLEARBYIDORDER {
		{
			this.command = new ClearByIdOrderCommand();
		}
	},
	MORE {
		{
			this.command = new MoreInfoCommand();
		}
	},
	NEWMENUCREAT {
		{
			this.command = new CreateNewMenuCommand();
		}
	},
	NEWMENU {
		{
			this.command = new NewMenuCommand();
		}
	},
	CLIENT {
		{
			this.command = new ClientPageCommand();
		}
	},
	COUNTCLIENT {
		{
			this.command = new CountClientCommand();
		}
	};
	ActionCommand command;
	public ActionCommand getCurrentCommand() {
		return command;
	}
}