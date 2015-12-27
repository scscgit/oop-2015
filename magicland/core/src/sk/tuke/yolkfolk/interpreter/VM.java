/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.interpreter;

import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * An InterpreterImpl we could say i guess.
 * The machine is most certainly virtual.
 * <p/>
 * Created by Steve on 23.12.2015.
 */
public class VM implements Interpreter
{
	//Objects
	private ArrayDeque<String> commands;

	public VM()
	{
		this.commands = new ArrayDeque<String>();
	}

	protected class InterpreterException extends Exception
	{
		InterpreterException(String message)
		{
			super("Interpreter error: " + message);
		}

		InterpreterException()
		{
			super("Interpreter error.");
		}
	}

	protected final class InterpreterNoInstructionsException extends InterpreterException
	{
		InterpreterNoInstructionsException()
		{
			super("Instruction is missing required operands.");
		}
	}

	protected final class InterpreterInvalidInstructionsException extends InterpreterException
	{
		InterpreterInvalidInstructionsException(String message)
		{
			super("Instruction is not valid: " + message);
		}

		InterpreterInvalidInstructionsException()
		{
			super("Instruction is not valid.");
		}
	}

	//Adds next string at the end of the queue
	protected final void add(String string)
	{
		if (this.commands.size() + 1 >= Interpreter.MAX_QUEUE_SIZE)
		{
			throw new StackOverflowError("Interpreter queue has overflowed");
		}
		this.commands.add(string);
	}

	//Returns next string in the order of execution and removes it permanently
	protected final String remove()
	{
		if (this.commands.size() > 0)
		{
			return this.commands.remove();
		}
		return "";
	}

	//Peek-a-boo, queue!
	protected final String peek()
	{
		if (this.commands.size() > 0)
		{
			return this.commands.peek();
		}
		return "";
	}

	//Queries the queue for state of its content (whether it's not empty)
	protected final boolean hasNext()
	{
		return !this.commands.isEmpty();
	}

	//Proclaims that the queue must not be empty, otherwise cancels the chain of responsibility by throwing an error
	protected final void assertNext() throws InterpreterException
	{
		if (!hasNext())
		{
			throw new InterpreterNoInstructionsException();
		}
	}

	//Replaces entire queue by rebuilding it from a new string that consists of more tokenized substrings
	private void replaceQueue(String commands)
	{
		//Old queue gets lost
		this.commands.clear();
		if (commands != null)
		{
			//C programmers might remember char* strtok()
			StringTokenizer tokenizer = new StringTokenizer(commands, Interpreter.TOKEN);
			while (tokenizer.hasMoreElements())
			{
				add(tokenizer.nextToken());
			}
		}
	}

	//Interface with a "user" that requests a service from the interpreter.
	//Its job is to basically run a chain of responsibility, starting with the execute() method.
	@Override
	public final void interpret(String commands)
	{
		//Each command interpretation uses its own stack
		replaceQueue(commands);

		//Executes commands one by one
		String command = peek();
		try
		{
			while (hasNext())
			{
				execute();
			}
		}
		catch (InterpreterException exception)
		{
			System.out.println(exception.getMessage());
			if (command != null)
			{
				System.out.println("Instructions starting from <" + command + "> have failed to get processed.");
			}
		}
	}

	//Main execute phase of a new command
	protected void execute() throws InterpreterException
	{
		assertNext();
		String cmd = remove();

		if (cmd.equals("set"))
		{
			executeSet();
		}
		else if (cmd.equals("player"))
		{
			executePlayer();
		}
		else
		{
			throw new InterpreterInvalidInstructionsException("Cannot understand command " + cmd + ".");
		}
	}

	//Set command is meant to set some new property
	protected void executeSet() throws InterpreterException
	{
		assertNext();
		String cmd = remove();

		if (cmd.equals("state"))
		{
			executeState();
		}
		else
		{
			throw new InterpreterInvalidInstructionsException("Set command does not understand <" + cmd + ">.");
		}
	}

	//Specifies a player name in the following command and returns to the main execute phase
	protected void executePlayer() throws InterpreterException
	{
		throw new InterpreterInvalidInstructionsException("Cannot find player from static context.");
	}

	//Changes state of a player
	protected void executeState() throws InterpreterException
	{
		throw new InterpreterInvalidInstructionsException("Player not specified.");
	}
}
