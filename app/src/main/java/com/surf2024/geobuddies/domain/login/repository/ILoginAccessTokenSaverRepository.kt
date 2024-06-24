import com.surf2024.geobuddies.domain.login.entity.LoginResponse
import retrofit2.Response

interface ILoginAccessTokenSaverRepository {
    fun saveAccessToken(
        response: Response<LoginResponse>?
    ): Boolean
}