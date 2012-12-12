package org.acm.mbaechi.formatter;

import java.math.BigDecimal;

public interface NumberFormatter {

	String format(BigDecimal number);

	String format(Double number);
}
