package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DishDetailScreen(
    dishId: Int,
    viewModel: DishViewModel,
    onBack: () -> Unit
) {
    // GIVEN: find the dish this screen is about.
    val dishes by viewModel.dishes.collectAsStateWithLifecycle()
    val dish = dishes.find { it.id == dishId }

    if (dish == null) {
        Text("Dish not found")
        return
    }

    // GIVEN: local UI state.
    var newStep by remember { mutableStateOf("") }
    var stepBeingEdited by remember { mutableStateOf<Recipe?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("< Back") }
        Text(dish.name, style = MaterialTheme.typography.headlineMedium)
        Text("Recipe steps", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

        // TODO 9 (6 pts) -- CREATE
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newStep,
                onValueChange = { newStep = it },
                label = { Text("New step") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                viewModel.addRecipe(dishId, newStep)
                newStep = ""
            }) { Text("Add") }
        }

        Spacer(Modifier.height(16.dp))

        // TODO 10 (10 pts) -- READ + DELETE
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(items = dish.recipes, key = { _, r -> r.id }) { index, recipe ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}. ${recipe.text}",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TextButton(onClick = { stepBeingEdited = recipe }) { Text("Edit") }
                        TextButton(onClick = {
                            viewModel.deleteRecipe(dishId, recipe.id)
                        }) { Text("Delete") }
                    }
                }
            }
        }
    }

    // TODO 11 (4 pts) -- UPDATE
    val editing = stepBeingEdited
    if (editing != null) {
        EditDialog(
            title = "Edit step",
            initialText = editing.text,
            onConfirm = { newText ->
                viewModel.updateRecipe(dishId, editing.id, newText)
                stepBeingEdited = null
            },
            onDismiss = { stepBeingEdited = null }
        )
    }
}
