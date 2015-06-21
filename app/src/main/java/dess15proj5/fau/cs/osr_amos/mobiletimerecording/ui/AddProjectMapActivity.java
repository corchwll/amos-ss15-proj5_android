/*
 *     Mobile Time Accounting
 *     Copyright (C) 2015
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dess15proj5.fau.cs.osr_amos.mobiletimerecording.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.R;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.DataAccessObjectFactory;
import dess15proj5.fau.cs.osr_amos.mobiletimerecording.persistence.ProjectsDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class AddProjectMapActivity extends AppCompatActivity
{
	private static final long minTime = 10000L;
	private static final float minDistance = 500.0f;

	private MapFragment mapFragment;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private Marker marker;

	/**
	 * This method is called in the android lifecycle when the activity is created.
	 *
	 * @param savedInstanceState this param contains several key value pairs in order to save the instance state
	 * methodtype initialization method
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_project_map);
		initToolbar();
		if(getSupportActionBar() != null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}
		initMapFragment();
	}

	/**
	 * This method initializes the toolbar.
	 *
	 * methodtype initialization method
	 */
	private void initToolbar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	private void initMapFragment()
	{
		mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(new OnMapReadyCallback()
		{
			@Override
			public void onMapReady(final GoogleMap googleMap)
			{
				googleMap.setMyLocationEnabled(true);
				googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener()
				{
					@Override
					public boolean onMyLocationButtonClick()
					{
						initLocation();
						return false;
					}
				});

				TypedValue latValue = new TypedValue();
				getResources().getValue(R.dimen.lat, latValue, true);
				float lat = latValue.getFloat();

				TypedValue lngValue = new TypedValue();
				getResources().getValue(R.dimen.lng, lngValue, true);
				float lng = lngValue.getFloat();

				marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
																.title(getResources().getString(R.string.marker)));
				mapFragment.getMap()
						   .moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

				googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
				{
					@Override
					public void onMapClick(LatLng latLng)
					{
						replaceMarker(latLng);
					}
				});
			}

		});
	}

	private void replaceMarker(LatLng latLng)
	{
		marker.remove();
		GoogleMap map = mapFragment.getMap();
		if(map != null)
		{
			marker = mapFragment.getMap().addMarker(new MarkerOptions().position(latLng));
			mapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLng(latLng));
		}
	}

	private void initLocation()
	{
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationListener()
		{
			@Override
			public void onLocationChanged(Location location)
			{
				replaceMarker(new LatLng(location.getLatitude(), location.getLongitude()));
			}

			@Override
			public void onStatusChanged(String s, int i, Bundle bundle)
			{
			}

			@Override
			public void onProviderEnabled(String s)
			{
			}

			@Override
			public void onProviderDisabled(String s)
			{
				showNoGPSEnabledToast();
			}
		};

		if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
		{
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance,
					locationListener);
		}
		if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
		}
	}

	//todo replace this with a dialog
	private void showNoGPSEnabledToast()
	{
		Toast.makeText(this, getResources().getString(R.string.noGPSWarning), Toast.LENGTH_SHORT).show();
	}

	/**
	 * Called as part of the activity lifecycle when the activity is going into the background, but has not been killed.
	 *
	 * methodtype initialization method
	 */
	@Override
	protected void onPause()
	{
		removeLocationListener();
		super.onPause();
	}

	public void removeLocationListener()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(locationListener);
			mapFragment.getMap().setMyLocationEnabled(false);
		}
	}

	/**
	 *  Called when the activity has detected the user's press of the back key.
	 *
	 * methodtype command method
	 */
	@Override
	public void onBackPressed()
	{
		finishActivityAndShowAnimation();
	}

	/**
	 *  Finish the activity and shows an animation
	 *
	 * methodtype command method
	 */
	private void finishActivityAndShowAnimation()
	{
		super.finish();
		overridePendingTransition(R.animator.empty_animator, R.animator.fade_out_right);
	}

	/**
	 * This method is called in the android lifecycle when a menu is created.
	 *
	 * @param menu the menu item which has to be created
	 * methodtype initialization method
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_add, menu);
		return true;
	}

	/**
	 * This method is called in the android lifecycle when a menu item is clicked on.
	 *
	 * @param item the item which was targeted
	 * @return true if there was an item clicked
	 * methodtype boolean query method
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				finishActivityAndShowAnimation();
				return true;
			case R.id.action_save_new_item:
				try
				{
					writeProjectInDb();
					startNextActivity();
				} catch(SQLException e)
				{
					Toast.makeText(this, "Could not save project due to database errors!", Toast.LENGTH_LONG).show();
				}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This method creates a new project and stores it to the database.
	 *
	 * @throws SQLException
	 * methodtype command method
	 */
	private void writeProjectInDb() throws SQLException
	{
		Intent intent = getIntent();
		String projectId = intent.getStringExtra("project_id");
		String projectName = intent.getStringExtra("project_name");

		Date date = getDateFromIntent();

		//todo save location in db

		ProjectsDAO projectsDAO = DataAccessObjectFactory.getInstance().createProjectsDAO(getBaseContext());
		projectsDAO.create(projectId, projectName, date, true, false, true);
	}

	private void startNextActivity()
	{
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	public Date getDateFromIntent()
	{
		Intent intent = getIntent();

		int year = intent.getIntExtra("year", 0);
		int month = intent.getIntExtra("month", 0);
		int day = intent.getIntExtra("day", 0);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
}
