import 'package:equatable/equatable.dart';

abstract class ElectionEvent extends Equatable {
  const ElectionEvent();

  @override
  List<Object> get props => [];
}

class LoadActiveElectionsEvent extends ElectionEvent {}
