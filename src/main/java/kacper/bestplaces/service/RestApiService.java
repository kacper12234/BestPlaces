package kacper.bestplaces.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.flickr4java.flickr.FlickrException;



public interface RestApiService{

	void getPlaces() throws IOException, FlickrException;

	
}
