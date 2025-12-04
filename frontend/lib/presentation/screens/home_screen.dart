import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:voting_bank/core/constants/colors.dart';
import 'package:voting_bank/core/constants/text_styles.dart';
import 'package:voting_bank/presentation/blocs/auth/auth_bloc.dart';
import 'package:voting_bank/presentation/blocs/auth/auth_event.dart';
import 'package:voting_bank/presentation/blocs/election/election_bloc.dart';
import 'package:voting_bank/presentation/blocs/election/election_event.dart';
import 'package:voting_bank/presentation/blocs/election/election_state.dart';
import 'package:voting_bank/presentation/widgets/election_card.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  void initState() {
    super.initState();
    // Load active elections when screen opens
    context.read<ElectionBloc>().add(LoadActiveElectionsEvent());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      appBar: AppBar(
        backgroundColor: AppColors.surface,
        elevation: 0,
        title: Text(
          'Active Elections',
          style: AppTextStyles.h3,
        ),
        actions: [
          IconButton(
            onPressed: () {
              context.read<AuthBloc>().add(LogoutEvent());
            },
            icon: const Icon(
              Icons.logout,
              color: AppColors.textPrimary,
            ),
          ),
        ],
      ),
      body: BlocBuilder<ElectionBloc, ElectionState>(
        builder: (context, state) {
          if (state is ElectionLoading) {
            return const Center(
              child: CircularProgressIndicator(
                color: AppColors.primary,
              ),
            );
          } else if (state is ElectionLoaded) {
            if (state.elections.isEmpty) {
              return Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.how_to_vote,
                      size: 80,
                      color: AppColors.textSecondary.withOpacity(0.5),
                    ),
                    const SizedBox(height: 16),
                    Text(
                      'No Active Elections',
                      style: AppTextStyles.h4,
                    ),
                    const SizedBox(height: 8),
                    Text(
                      'There are no active elections at the moment.',
                      style: AppTextStyles.bodyMedium.copyWith(
                        color: AppColors.textSecondary,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ],
                ),
              );
            }

            return RefreshIndicator(
              onRefresh: () async {
                context.read<ElectionBloc>().add(LoadActiveElectionsEvent());
              },
              color: AppColors.primary,
              child: ListView.builder(
                padding: const EdgeInsets.all(16),
                itemCount: state.elections.length,
                itemBuilder: (context, index) {
                  final election = state.elections[index];
                  return Padding(
                    padding: const EdgeInsets.only(bottom: 16),
                    child: ElectionCard(election: election),
                  );
                },
              ),
            );
          } else if (state is ElectionError) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(
                    Icons.error_outline,
                    size: 80,
                    color: AppColors.error,
                  ),
                  const SizedBox(height: 16),
                  Text(
                    'Error Loading Elections',
                    style: AppTextStyles.h4.copyWith(
                      color: AppColors.error,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Text(
                    state.message,
                    style: AppTextStyles.bodyMedium.copyWith(
                      color: AppColors.textSecondary,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 24),
                  ElevatedButton(
                    onPressed: () {
                      context.read<ElectionBloc>().add(LoadActiveElectionsEvent());
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.primary,
                      padding: const EdgeInsets.symmetric(
                        horizontal: 32,
                        vertical: 12,
                      ),
                    ),
                    child: Text(
                      'Try Again',
                      style: AppTextStyles.buttonMedium.copyWith(
                        color: Colors.white,
                      ),
                    ),
                  ),
                ],
              ),
            );
          }

          return const SizedBox.shrink();
        },
      ),
    );
  }
}
