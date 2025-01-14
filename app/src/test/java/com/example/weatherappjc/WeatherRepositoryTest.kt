import com.example.weatherappjc.apis.WeatherApi
import com.example.weatherappjc.models.Condition
import com.example.weatherappjc.models.Current
import com.example.weatherappjc.models.Location
import com.example.weatherappjc.models.WeatherResponse
import com.example.weatherappjc.repos.WeatherRepository
import com.example.weatherappjc.utils.AppConstants
import com.example.weatherappjc.utils.NetworkUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class) // Opt-in for using experimental coroutines APIs
class WeatherRepositoryTest {

    @Mock
    lateinit var mockApiService: WeatherApi

    @Mock
    lateinit var mockNetworkUtils: NetworkUtils

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = WeatherRepository(mockApiService, mockNetworkUtils)
    }

    @Test
    fun `fetchWeather should return weather data when network is available and API call succeeds`() = runBlockingTest {
        // Given
        val city = "London"
        val expectedResponse = WeatherResponse(
            location = Location(name = "London"),
            current = Current(temp_f = 70.0F, wind_kph = 15.0F, condition = Condition(text = "Sunny"))
        )
        whenever(mockNetworkUtils.isNetworkAvailable()).thenReturn(true)
        whenever(mockApiService.getWeather(AppConstants.API_KEY, city)).thenReturn(expectedResponse)

        // When
        val result = weatherRepository.fetchWeather(city)

        // Then
        assertNotNull(result)  // Should not be null since network is available and API call is successful
        assertEquals(expectedResponse, result) // Ensure that the returned response matches the expected one
    }

    @Test
    fun `fetchWeather should return null when network is not available`() = runBlockingTest {
        // Given
        val city = "London"
        whenever(mockNetworkUtils.isNetworkAvailable()).thenReturn(false)

        // When
        val result = weatherRepository.fetchWeather(city)

        // Then
        assertNull(result)  // Should return null since there is no network
    }

    @Test
    fun `fetchWeather should return null when an exception occurs during API call`() = runBlockingTest {
        // Given
        val city = "London"
        whenever(mockNetworkUtils.isNetworkAvailable()).thenReturn(true)
        whenever(mockApiService.getWeather(AppConstants.API_KEY, city)).thenThrow(Exception("API error"))

        // When
        val result = weatherRepository.fetchWeather(city)

        // Then
        assertNull(result)  // Should return null because an exception occurs during the API call
    }
}
