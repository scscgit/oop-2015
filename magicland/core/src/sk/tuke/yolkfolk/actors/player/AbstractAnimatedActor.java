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

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actors.AbstractActor;

/**
 * Implementacia podpory roznorodych animacii pohybu, na ktore ma pravo kazdy hrac, pomocou verejnych metod
 *
 * Created by Steve on 11.12.2015.
 */
public abstract class AbstractAnimatedActor extends AbstractActor implements AnimatedMovement
{
	//Animations
	private Animation aniLeft, aniRight, aniJump, aniJumpLeft, aniJumpRight;
	private boolean isAnimationLeft, isAnimationRight, isAnimationJump;

	public AbstractAnimatedActor(String name, String animationString, int animationX, int animationY)
	{
		super(name, animationString, animationX, animationY);

		//Default animacie su rovnake ako zakladna animacia, odporuca sa nahradit ich v konstruktoroch
		setAnimationLeft(getAnimation());
		setAnimationRight(getAnimation());
		setAnimationJump(getAnimation());
		setAnimationJumpLeft(getAnimation());
		setAnimationJumpRight(getAnimation());

		this.isAnimationLeft = false;
		this.isAnimationRight = false;
		this.isAnimationJump = false;
	}

	public void setAnimationLeft(Animation aniLeft)
	{
		this.aniLeft = aniLeft;
	}
	public void setAnimationRight(Animation aniRight)
	{
		this.aniRight = aniRight;
	}
	public void setAnimationJump(Animation aniJump)
	{
		this.aniJump = aniJump;
	}
	public void setAnimationJumpLeft(Animation aniJumpLeft)
	{
		this.aniJumpLeft = aniJumpLeft;
	}
	public void setAnimationJumpRight(Animation aniJumpRight)
	{
		this.aniJumpRight = aniJumpRight;
	}

	public void runAnimationLeft()
	{
		this.isAnimationLeft = true;
		updateAnimation();
	}
	public void runAnimationRight()
	{
		this.isAnimationRight = true;
		updateAnimation();
	}
	public void runAnimationJump()
	{
		this.isAnimationJump = true;
		updateAnimation();
	}

	public void stopAnimationLeft()
	{
		this.isAnimationLeft = false;
		updateAnimation();
	}
	public void stopAnimationRight()
	{
		this.isAnimationRight = false;
		updateAnimation();
	}
	public void stopAnimationJump()
	{
		this.isAnimationJump = false;
		updateAnimation();
	}

	private void updateAnimation()
	{
		if
			(
				this.isAnimationJump
				&&
				(
					(this.isAnimationLeft && this.isAnimationRight)
						||
						(!this.isAnimationLeft && !this.isAnimationRight)
				)
			)
		{
			//Only jump
			setAnimation(this.aniJump);
		}
		else if(this.isAnimationJump && this.isAnimationLeft)
		{
			//Jump left
			setAnimation(this.aniJumpLeft);
		}
		else if(this.isAnimationJump /* && this.isAnimationRight*/)
		{
			//Jump right
			setAnimation(this.aniJumpRight);
		}
		else if
			(
				(this.isAnimationLeft && this.isAnimationRight)
				||
				(!this.isAnimationLeft && !this.isAnimationRight)
			)
		{
			//Default animation
			setAnimation();
		}
		else if(this.isAnimationLeft)
		{
			//Move left
			setAnimation(this.aniLeft);
		}
		else if(this.isAnimationRight)
		{
			//Move right
			setAnimation(this.aniRight);
		}
		else
		{
			//Default animation
			setAnimation();
		}
	}
}