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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actions.Cheats;
import sk.tuke.yolkfolk.actions.Exit;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.input.CustomInput;

/**
 * Trieda definujuca spolocne funkcionality pre stavy, v ktorych sa Dizzy nachadza vo vzduchu.
 *
 * Created by Steve on 17.12.2015.
 */
public class AbstractAirborneState extends AbstractPlayerState
{
	public AbstractAirborneState(Player player)
	{
		super(player);
	}

	//Podla stlacenej klavesy umozni dodatocne skoky z dovodu lietania
	protected void fly()
	{
		if(CustomInput.left())
		{
			setStateJumping(PlayerWalking.Direction.LEFT);
		}
		else if(CustomInput.right())
		{
			setStateJumping(PlayerWalking.Direction.RIGHT);
		}
		else
		{
			setStateJumping(PlayerWalking.Direction.UP);
		}
	}

	@Override
	public void act()
	{
		//Vykona pozadovane akcie s vynimkou pouzitia predmetu
		resetActions();
		addAction(new Exit());
		addAction(new Cheats());
		runActions();

		//Pripad lietania
		if(getPlayer().isFlyable() && CustomInput.up())
		{
			fly();
		}

		//Necha playera vykonat svoje operacie po novom pohybe
		getPlayer().afterMovement();

		//Zastavi skok iba po dopade na zem //TODO: reimplement after gradle fix gets released
		float ySpeed = PhysicsHelper.getLinearVelocity(getPlayer())[1];
		if(ySpeed==0)
		//if(getPlayer().isOnGround())
		{
			//getPlayer().stopAnimationJump();
			setStateStanding();
		}

		//Ak hrac zomrel, tak nastavim prislusny stav
		if(isPlayerDead())
		{
			setStateDying();
		}
	}
}
