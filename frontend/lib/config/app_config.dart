class AppConfig {
  static const String appName = 'Voting Bank';
  static const String apiBaseUrl = 'http://localhost:8080/api/v1';
  static const int apiTimeout = 30000; // 30 seconds
  static const String jwtTokenKey = 'jwt_token';
  static const String refreshTokenKey = 'refresh_token';
  static const String userKey = 'user_data';
}
