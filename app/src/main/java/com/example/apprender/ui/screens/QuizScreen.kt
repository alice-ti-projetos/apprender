package com.example.apprender.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apprender.R
import com.example.apprender.ui.components.BigButton
import com.example.apprender.ui.components.ScreenScaffold

@Composable
fun QuizScreen(
    lessonId: String,
    onNavigateToComplete: () -> Unit,
    onBackClick: () -> Unit
) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    val correctOptionIndex = 1 // Option B is correct

    ScreenScaffold(
        title = stringResource(R.string.title_quiz),
        onBackClick = onBackClick
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = stringResource(R.string.quiz_question),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Option A
            QuizOptionCard(
                text = stringResource(R.string.quiz_option_a),
                isSelected = selectedOption == 0,
                onClick = { selectedOption = 0 }
            )

            // Option B (Correct)
            QuizOptionCard(
                text = stringResource(R.string.quiz_option_b),
                isSelected = selectedOption == 1,
                onClick = { selectedOption = 1 }
            )

            // Option C
            QuizOptionCard(
                text = stringResource(R.string.quiz_option_c),
                isSelected = selectedOption == 2,
                onClick = { selectedOption = 2 }
            )

            // Feedback Card if option selected
            selectedOption?.let { selected ->
                val isCorrect = selected == correctOptionIndex
                val feedbackText = if (isCorrect) {
                    stringResource(R.string.quiz_correct_feedback)
                } else {
                    stringResource(R.string.quiz_incorrect_feedback)
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.errorContainer,
                        contentColor = if (isCorrect) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text(
                        text = feedbackText,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(18.dp)
                    )
                }

                if (isCorrect) {
                    BigButton(
                        text = stringResource(R.string.btn_finish),
                        onClick = onNavigateToComplete,
                        icon = Icons.Default.CheckCircle,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizOptionCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface

    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.outlinedCardColors(containerColor = containerColor),
        border = BorderStroke(if (isSelected) 3.dp else 1.5.dp, borderColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            modifier = Modifier.padding(18.dp)
        )
    }
}
