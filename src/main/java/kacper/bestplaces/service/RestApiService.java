package kacper.bestplaces.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.flickr4java.flickr.FlickrException;



public interface RestApiService{

	public void getPlaces() throws MalformedURLException, IOException, FlickrException;

	
}
