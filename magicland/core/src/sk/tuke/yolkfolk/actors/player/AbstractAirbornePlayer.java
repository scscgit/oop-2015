/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
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

package sk.tuke.yolkfolk.actors.player;

/**
 * Implementacia padania, parametrov skakania a stavu lietania v kombinacii s dedenim podpory animovaneho pohybu.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public abstract class AbstractAirbornePlayer extends AbstractAnimatedActor implements AirborneSupport, Player
{
	//Constants
	public static final float JMP_TO_FLY_RATE = 0.72f;

	//Variables for falling purposes
	private float fallSpeed;
	private float fallLimit;
	private float fallDamageRatio;
	private float lastY;
	private boolean lastYequals;

	//Variables
	private boolean flyable;
	private float jumpHeight;

	public AbstractAirbornePlayer(String name, String animationString, int animationX, int animationY)
	{
		super(name, animationString, animationX, animationY);

		//Default parametre hraca
		setFlyable(false);
		setJumpHeight(0); //Inicializacia vysky skoku.
		this.lastY = getY();
		this.lastYequals = false;

		//Default parametre tykajuce sa padania
		setFallLimit(0);
		setFallDamageRatio(20);
	}

	@Override
	public void act()
	{
		//Osetrenie zistovania, ci je hrac na zemi
		this.lastYequals = Math.abs(this.lastY - getY()) < AbstractPlayer.DELTA_Y_IS_ZERO;
		this.lastY = getY();
	}

	//Oznamuje hracovi rychlost padania a po dopade na zem urcitou rychlostou (linear velocity v smere y) udeli hracovi relevantne zranenie.
	public final void fall(float ySpeed)
	{
		//Ak pada, tak sa najvacsia hodnota rychlosti zapamata. Ak prestal padat, povodna hodnota urcuje zranenie.
		if (/*ySpeed < 0 &&*/ -this.fallSpeed > ySpeed)
		{
			this.fallSpeed = -ySpeed;
		}
		else
		{
			float speed = this.fallSpeed - getFallLimit();
			this.fallSpeed = 0f;

			//Zranenie dostane iba ak pretal padat a nelieta
			if (speed > 0 && !isFlyable())
			{
				decreaseEnergy((int) (speed * getFallDamageRatio()));
			}
		}

		/*
		Stary kod: verzia ktora riesila rozdiel suradnic
		Nastavi aktualnu poziciu suradnice Y hraca ako novu poziciu dopadu a v pripade velkeho rozdielu od minulej hodnoty udeli hracovi zranenie.
		float y = getY();
		float fallDistance = this.fallPosition - y;
		if (getFallDamageRatio() > 0 && fallDistance > 0 && fallDistance > getFallLimit())
		{
			decreaseEnergy((int) ((fallDistance - getFallLimit()) * getFallDamageRatio()));
		}
		this.fallPosition = y;
		*/
	}

	//Kriticka rychlost, akou musi hrac padat, aby mu bolo udelene poskodenie z padu.
	public final void setFallLimit(float fallLimit)
	{
		this.fallLimit = fallLimit;
	}
	public final float getFallLimit()
	{
		return fallLimit;
	}
	//Koeficient, ktorym sa vynasobi rozdiel rychlosti padania a kritickej rychlosti pred udelenim zranenia o rovnakej hodnote.
	public final void setFallDamageRatio(float fallDamageRatio)
	{
		this.fallDamageRatio = fallDamageRatio;
	}
	public final float getFallDamageRatio()
	{
		return fallDamageRatio;
	}
	//Vyska skoku
	public void setJumpHeight(float jumpHeight)
	{
		this.jumpHeight = jumpHeight;
	}
	public float getJumpHeight()
	{
		//V pripade lietania je potrebne redukovat rychlost "skakania po vzduchu"
		if (isFlyable())
		{
			return this.jumpHeight * AbstractAirbornePlayer.JMP_TO_FLY_RATE;
		}
		else
		{
			return this.jumpHeight;
		}
	}

	//Nastavi schopnost hraca lietat namiesto skakania
	public void setFlyable(boolean flying)
	{
		this.flyable = flying;

		//Ak prave padal z velkej vysky, tak bude zachraneny a po dopade na zem nezomrie
		fall(0f);
	}
	public boolean isFlyable()
	{
		return this.flyable;
	}

	//Hotfix for GameLib inability to distinguish between standing and falling
	public final boolean noVerticalChange()
	{
		return this.lastYequals;
	}
}
