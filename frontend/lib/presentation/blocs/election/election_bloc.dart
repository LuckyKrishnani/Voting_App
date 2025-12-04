import 'package:flutter_bloc/flutter_bloc.dart';
import 'election_event.dart';
import 'election_state.dart';

class ElectionBloc extends Bloc<ElectionEvent, ElectionState> {
  ElectionBloc() : super(ElectionInitial()) {
    on<LoadActiveElectionsEvent>(_onLoadActiveElections);
  }

  Future<void> _onLoadActiveElections(
    LoadActiveElectionsEvent event,
    Emitter<ElectionState> emit,
  ) async {
    emit(ElectionLoading());
    try {
      // TODO: Implement loading elections logic
      await Future.delayed(const Duration(seconds: 1));
      emit(const ElectionLoaded([]));
    } catch (e) {
      emit(ElectionError('Failed to load elections'));
    }
  }
}
