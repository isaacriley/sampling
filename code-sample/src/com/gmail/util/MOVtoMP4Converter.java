/**
 * 
 */
package com.gmail.util;

import java.io.IOException;

import com.gmail.constants.TestConstants;

/**
 * As name suggests, this class converts .mov files (the default video format of the session) to the more efficient 
 * and universal .mp4 file format 
 * @author IRiley
 *
 */
public final class MOVtoMP4Converter {

	private MOVtoMP4Converter() {}

	/**
	 * Converts a .mov file to an .mp4 (for Windows targets)
	 * @param nameOfRecordedMOVFile the name of the .mov file; the output (.mp4) file will retain the original name
	 * of the .mov file (i.e., we just simply change the extension) 
	 */
	public static void convertMovToMp4_ffmpeg_windows(String nameOfRecordedMOVFile) {
		try {
			//System.out.println(recordedVideoFilename);
			Runtime.getRuntime().exec("cmd /c start "+	TestConstants.MP4_CONVERTER+" "+nameOfRecordedMOVFile);
		} catch (IOException e) {
			AutomationHelper.logExceptionAndThrowable("Couldn't run batch file", e);
			e.printStackTrace();
		}
	}

	/**
	 * Converts a .mov file to an .mp4 (for Windows targets)
	 * @param nameOfRecordedMOVFile name of the .mov file
	 * @param nameOfMP4File user defined name of the .mp4 file
	 */
	public static void convertMovToMp4_ffmpeg_windows(String nameOfRecordedMOVFile, String nameOfMP4File) {
		try {
			//System.out.println(recordedVideoFilename);
			Runtime.getRuntime().exec("cmd /c start "+
					TestConstants.MP4_CONVERTER+" "+nameOfRecordedMOVFile+"  "+nameOfMP4File);
			} catch (Exception e) {
			AutomationHelper.logExceptionAndThrowable("Couldn't run batch file\n", e);
			e.printStackTrace();
		}
	}
	
	/*
	public static String convertMovToMp4_xuggle(String pathToMovFile) {
		String convertedMp4FFilename= pathToMovFile+".mp4";
		//create media from input file
		IMediaReader inputMediaReader = ToolFactory.makeReader(pathToMovFile);
		//create output media writer
		IMediaWriter outputMediaWriter = 
				ToolFactory.makeWriter(convertedMp4FFilename, inputMediaReader);
		//this adds the media writer to the reader
		inputMediaReader.addListener(outputMediaWriter);
		//create a media viewer with the video/audio statics enabled
		IMediaViewer mediaViewer = ToolFactory.makeViewer(true);
		//adds viewer that allows us to see the decoded media
		inputMediaReader.addListener(mediaViewer);
		//now decode
		while(inputMediaReader.readPacket()== null);
		AutomationHelper.logIt("\n** MP4 Video: "+convertedMp4FFilename+"**\n");
		
		return convertedMp4FFilename;
	}
	*/
}
