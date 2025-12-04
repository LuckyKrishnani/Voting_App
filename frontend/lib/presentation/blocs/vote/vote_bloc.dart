import 'package:flutter_bloc/flutter_bloc.dart';
import 'vote_event.dart';
import 'vote_state.dart';

class VoteBloc extends Bloc<VoteEvent, VoteState> {
  VoteBloc() : super(VoteInitial()) {
    on<CastVoteEvent>(_onCastVote);
  }

  Future<void> _onCastVote(
    CastVoteEvent event,
    Emitter<VoteState> emit,
  ) async {
    emit(VoteLoading());
    try {
      // TODO: Implement vote casting logic
      await Future.delayed(const Duration(seconds: 1));
      emit(VoteSuccess());
    } catch (e) {
      emit(VoteError('Failed to cast vote'));
    }
  }
}
