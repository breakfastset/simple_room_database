package com.example.mysqldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysqldemo.ui.theme.MySQLDemoTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            MemberDatabase.getDatabase(applicationContext).memberDao().getAllMembers()
        }
        enableEdgeToEdge()
        setContent {
            MySQLDemoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MemberDropdown(memberDb = MemberDatabase.getDatabase(applicationContext))
                }
            }
        }
    }
}

@Composable
fun MemberDropdown(memberDb: MemberDatabase) {
    var memberList by remember { mutableStateOf<List<Member>>(emptyList()) }
    var expanded by remember {mutableStateOf(false)}
    var selectedMember by remember {mutableStateOf("Select Member")}

    LaunchedEffect(Unit) {
        memberList = memberDb.memberDao().getAllMembers()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Simple clickable button to anchor the dropdown menu
        Button(onClick = { expanded = true }) {
            Text(text = selectedMember)
        }

        // The dropdown list
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            memberList.forEach { member ->
                DropdownMenuItem(
                    text = { Text(member.id.toString()) },
                    onClick = {
                        selectedMember = member.name
                        expanded = false
                    }
                )
            }
        }
    }
}