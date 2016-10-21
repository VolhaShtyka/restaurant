package commandEnum;
import command.ActionCommand;
import command.AllowanceCommand;
import command.ClearByIdOrderCommand;
import command.ClearCommand;
import command.CookingCommand;
import command.CountClientCommand;
import command.CountCommand;
import command.CreateNewMenuCommand;
import command.OrderCommand;
import command.ReadyCommand;
import command.DeleteCommand;
import command.DiscountCommand;
import command.LanguageCommand;
import command.LoginCommand;
import command.LogoutCommand;
import command.MoreInfoCommand;
import command.NewMenuCommand;

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