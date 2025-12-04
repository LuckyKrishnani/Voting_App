import 'package:flutter_bloc/flutter_bloc.dart';
import 'auth_event.dart';
import 'auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  AuthBloc() : super(AuthInitial()) {
    on<LoginEvent>(_onLogin);
    on<RegisterEvent>(_onRegister);
    on<LogoutEvent>(_onLogout);
  }

  Future<void> _onLogin(LoginEvent event, Emitter<AuthState> emit) async {
    emit(AuthLoading());
    try {
      // TODO: Implement login logic
      await Future.delayed(const Duration(seconds: 1));
      emit(AuthAuthenticated());
    } catch (e) {
      emit(AuthError('Login failed'));
    }
  }

  Future<void> _onRegister(RegisterEvent event, Emitter<AuthState> emit) async {
    emit(AuthLoading());
    try {
      // TODO: Implement register logic
      await Future.delayed(const Duration(seconds: 1));
      emit(AuthAuthenticated());
    } catch (e) {
      emit(AuthError('Registration failed'));
    }
  }

  Future<void> _onLogout(LogoutEvent event, Emitter<AuthState> emit) async {
    emit(AuthInitial());
  }
}
