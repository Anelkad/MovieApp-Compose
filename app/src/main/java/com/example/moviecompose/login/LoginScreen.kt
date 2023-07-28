package com.example.moviecompose.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviecompose.R
import com.example.moviecompose.ui.theme.MovieComposeTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = modifier
    ){
        Column {
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        color = Color.Gray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = with(LocalDensity.current) {
                        dimensionResource(R.dimen.font_size_20).toSp()
                    }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height_10)))
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                label = {
                    Text(
                        text = stringResource(R.string.password),
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = with(LocalDensity.current) {
                        dimensionResource(R.dimen.font_size_20).toSp()
                    }
                ),
                visualTransformation = remember { PasswordVisualTransformation() },
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {}
                    ){
                        Icon(
                            painter = painterResource(R.drawable.baseline_remove_red_eye_24),
                            contentDescription = null
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_20)))
            ClickableText(
                text = buildAnnotatedString {
                    append(stringResource(R.string.forgot_password))
                },
                onClick = {}
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height_10)))
            Button(
                onClick = {},
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_5)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_10)),
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = stringResource(R.string.login))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height_10)))
            Button(
                onClick = {},
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_5)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                border = BorderStroke(
                    width = dimensionResource(R.dimen.border_05),
                    color = Color.LightGray
                ),
                contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_10)),
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = stringResource(R.string.signup))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MovieComposeTheme {
        LoginScreen(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_20))
        )
    }
}