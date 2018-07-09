package com.tiny.web.controller.ocr.model;

import com.tiny.web.controller.ocr.util.Constants;
import org.apache.log4j.Logger;

public class Detect extends Region{

	private final static Logger logger = Logger.getLogger(Detect.class);

	private String name;

	private Constants.DetectPolicy policy;

	private int horizontalThreshold = Constants.HORIZONTAL_THRESHHOLD;

	private int verticalThreshold = Constants.VERTICAL_THRESHHOLD;

	public int getHorizontalThreshold()
	{
		return horizontalThreshold;
	}

	public int getVerticalThreshold()
	{
		return verticalThreshold;
	}

	public void setHorizontalThreshold(int horizontalThreshold) {
		this.horizontalThreshold = horizontalThreshold;
	}

	public void setVerticalThreshold(int verticalThreshold) {
		this.verticalThreshold = verticalThreshold;
	}

	public void setHorizontalThreshold(String horizontalThreshold)
	{
		try
		{
			this.horizontalThreshold = Integer.parseInt(horizontalThreshold);
		}
		catch (NumberFormatException e)
		{
			logger.error(e.getMessage());
			this.horizontalThreshold = Constants.HORIZONTAL_THRESHHOLD;
		}
	}

	public void setVerticalThreshold(String verticalThreshold)
	{
		try
		{
			this.verticalThreshold = Integer.parseInt(verticalThreshold);
		}
		catch (NumberFormatException e)
		{
			logger.error(e.getMessage());
			this.horizontalThreshold = Constants.HORIZONTAL_THRESHHOLD;
		}
	}

	public Detect()
	{
	}

	public Detect(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public Detect setName(String name)
	{
		this.name = name;
		return this;
	}

	public Constants.DetectPolicy getPolicy()
	{
		return policy;
	}

	public Detect setPolicy(Constants.DetectPolicy policy)
	{
		this.policy = policy;
		return this;
	}

}
