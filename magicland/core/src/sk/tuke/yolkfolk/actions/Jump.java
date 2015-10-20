/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
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
 * Jump action of Player.
 *
 * Created by Steve on 2.12.2015.
 */
public class Jump extends AbstractAction
{
	public Jump()
	{
		super();
	}

	//Constants
	//public static final float MIN_JMP_SPEED = 1e-6f;

	@Override
	public void doAction(Actor actor)
	{
		if(actor instanceof Player)
		{
			Player player = (Player) actor;

			//Vykonanie skoku, lietnaia alebo plavania v pripade stlacenej klavesy
			if (Input.isKeyPressed(Input.Key.UP) || Input.isKeyPressed(Input.Key.W))
			{
				if(player.isFlyable())
				{
					PhysicsHelper.applyForce(player, 0f, player.getStep()*0.2f);
					player.afterMovement();

					player.runAnimationJump(); //V pripade custom animacie lietania/plavania bude hrac obsahovat pozadovany kod v tejto metode
				}
				else if (player.isOnGround() || PhysicsHelper.getLinearVelocity(player)[1] == 0 /* || PhysicsHelper.getLinearVelocity(player)[1] < MIN_JMP_SPEED*/)
				{
					//PhysicsHelper.applyImpulse(player,0f,0.2f); //Podobna funkcionalita ako applyForce s y=30f
					PhysicsHelper.applyForce(player, 0f, player.getStep()*2.5f );
					player.afterMovement();

					player.runAnimationJump();
				}
			}
			//Special behavior if, instead, I am checking for PhysicsHelper.getLinearVelocity(player)[1] == 0 in executeActions() of AbstractPlayer
			/*else
			{
				player.stopAnimationJump();
			}*/
		}

		//Do implicit action
		super.doAction(actor);
	}
}
