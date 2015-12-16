/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actions;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Input;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Created by Steve on 23.11.2015.
 */
@Deprecated
public class MoveRight extends AbstractAction
{
	public MoveRight()
	{
		super();
	}

	@Override
	public void doAction(Actor actor)
	{
		if(actor instanceof Player)
		{
			Player player = (Player) actor;

			if((Input.isKeyPressed(Input.Key.RIGHT) || Input.isKeyPressed(Input.Key.D)))
			{
				//actor.setPosition(actor.getX()-((AbstractActor) actor).getStep(),actor.getY()); //without using Physix
				PhysicsHelper.setLinearVelocity(actor, player.getStep(), PhysicsHelper.getLinearVelocity(actor)[1]);
				//PhysicsHelper.applyForce(actor, player.getStep(), 0);
				player.afterMovement();

				player.runAnimationRight();
			}
			else
			{
				player.stopAnimationRight();
			}
		}

		//Do implicit action
		super.doAction(actor);
	}
}
