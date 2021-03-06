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

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Animation;

/**
 * Abstraktna podpora pre dekorator hercov podporujucich pohyb viacerymi smermi.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public abstract class AbstractAnimatedDecorator extends AbstractActorDecorator implements AnimatedMovement
{
	//Dekorovany actor
	private AnimatedMovement actor;

	public AbstractAnimatedDecorator(AnimatedMovement actor)
	{
		super(actor);
		this.actor = actor;
	}

	@Override
	public void setAnimationNormal(Animation aniNormal)
	{
		this.actor.setAnimationNormal(aniNormal);
	}
	@Override
	public void setAnimation()
	{
		this.actor.setAnimation();
	}
	@Override
	public void setAnimationLeft(Animation aniLeft)
	{
		this.actor.setAnimationLeft(aniLeft);
	}
	@Override
	public void setAnimationRight(Animation aniRight)
	{
		this.actor.setAnimationRight(aniRight);
	}
	@Override
	public void setAnimationJump(Animation aniJump)
	{
		this.actor.setAnimationJump(aniJump);
	}
	@Override
	public void setAnimationJumpLeft(Animation aniJumpLeft)
	{
		this.actor.setAnimationJumpLeft(aniJumpLeft);
	}
	@Override
	public void setAnimationJumpRight(Animation aniJumpRight)
	{
		this.actor.setAnimationJumpRight(aniJumpRight);
	}
	@Override
	public void runAnimationLeft()
	{
		this.actor.runAnimationLeft();
	}
	@Override
	public void runAnimationRight()
	{
		this.actor.runAnimationRight();
	}
	@Override
	public void runAnimationJump()
	{
		this.actor.runAnimationJump();
	}
	@Override
	public void stopAnimationLeft()
	{
		this.actor.stopAnimationLeft();
	}
	@Override
	public void stopAnimationRight()
	{
		this.actor.stopAnimationRight();
	}
	@Override
	public void stopAnimationJump()
	{
		this.actor.stopAnimationJump();
	}
}
