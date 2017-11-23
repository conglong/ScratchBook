using System;

namespace FractionFormatter
{
	public interface FractionRounder <T>
	{
		T round(T number);
	}
}

