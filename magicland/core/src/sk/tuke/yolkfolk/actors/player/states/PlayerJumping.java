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

package sk.tuke.yolkfolk.actors.player.states;

import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * State of being high.
 * Cannot get higher or change direction until falling on the ground.
 * Also, cannot use items.
 * <p/>
 * Created by Steve on 11.12.2015.
 */
public class PlayerJumping extends AbstractAirborneState
{
	//Variables
	//Faza padania (falling=true) alebo vyskoku (falling=false)
	private boolean falling;
	//Smer skoku
	private PlayerState.Direction direction;

	public PlayerJumping(Player player, PlayerState.Direction direction)
	{
		super(player);
		this.direction = direction;

		//Prva faza skakania je vyskok, cez act() sa po vyskoku tato hodnota prestavi na true
		this.falling = false;

		//Vykona skok v zadanom smere
		jump();

		//Nastavi relevantne animacie
		updateAnimation();
	}

	private void updateAnimation()
	{
		if (this.direction == PlayerState.Direction.UP)
		{
			getPlayer().stopAnimationLeft();
			getPlayer().stopAnimationRight();
		}
		else
		{
			updateAnimDir(this.direction);
		}
		getPlayer().runAnimationJump();
	}

	//Vykona skok v zadanom smere
	private void jump()
	{
		//Zastavi pohyb pred skokom z dovodu ako funguje kniznica sprostredkujuca PhysicsHelper
		//float oldHorizontalVelocity = PhysicsHelper.getLinearVelocity(getPlayer())[0];
		stopVelocity();

		//Vyvinie silu v smere proti gravitacii
		PhysicsHelper.applyForce(getPlayer(), 0f, getPlayer().getJumpHeight());

		//V pripade skoku v zadanom horizontalnom smere explicitne zacnem dany pohyb
		setVelocity(this.direction);
	}

	@Override
	public void act()
	{
		//Na vrchu skoku sa pokusi pohnut v smere, v ktorom skace, aby sa nezasekol pri vysokej prekazke
		//Note for self: zaseknutie sa v nekonecnom skoku som tu opravil pridanim OBOCH rovnosti: < na <=, > na >=
		if (!this.falling && PhysicsHelper.getLinearVelocity(getPlayer())[1] <= 0)
		{
			setVelocity(this.direction);
			this.falling = true;
			resetLastSpeedY();
		}

		//Experimentalne skombinovanie dvoch podmienok, pricom prechod budem robit vzdy najprv na stav Falling:
		//Ak zacal hrac spomalovat padanie, tak budem predpokladat, ze dopadol na zem
		//Ak pada privelkou rychlostou, tak uz neskace dobrovolne
		if ((this.falling && getLastSpeedY() >= speedY() || isPlayerFalling()))
		{
			interpret("set state falling");
		}
		//Vykona operacie, ktore ma vykonat, ked je vo vzduchu
		else
		{
			super.act();
		}
	}
}
