package com.vitalyv.jetpacktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitalyv.jetpacktest.ui.theme.JetPackTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackTestTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding){
        OnboardingScreen(onContinueClicker = {shouldShowOnboarding = false})
    }else{
        Greetings()
    }
}

@Composable
fun Greetings(names: List<String> = List(500){"$it"}) {
    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Row() {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                LazyColumn {
                    item { Text(text = "Header") }
                    items(names){name -> Greeting(name)}
                }
            }
        }


    }
}

@Composable
fun Greeting(name: String) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(targetValue = if (expanded) 48.dp else 0.dp, animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    ))
    Surface(
        with(Modifier) { padding(horizontal = 8.dp, vertical = 4.dp) },
        color = MaterialTheme.colorScheme.background
    ) {
        Row(modifier = Modifier.padding(24.dp).animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ))
        {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello №$name",style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold)
                )
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(R.string.show_less)

                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }

    }
}

@Composable
fun OnboardingScreen(onContinueClicker: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!",style = MaterialTheme.typography.headlineSmall)
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicker
        ) {
            Text("Continue", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JetPackTestTheme {
        OnboardingScreen(onContinueClicker = {})
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview(names: List<String> = listOf("World", "Ivan")) {
    JetPackTestTheme {
        Greetings()
    }
}