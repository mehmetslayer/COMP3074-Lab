package ca.gbc.comp3074.mehmeteminonem.lab03

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable

fun CourseScreen(){
    val context = LocalContext.current
    val courses = remember {
        mutableStateListOf<Course>().apply {
            addAll(loadCourse(context))
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                val courseNumber = courses.size + 1

                val newCourse = Course(
                    code = "COMP$courseNumber",
                    name = "New course $courseNumber"
                )
                courses.add(newCourse)
            }
        ){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add icon"
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text("Add Course")
        }
        LazyCourseList(courses)
    }


}

@Composable
fun CourseItem(course: Course){

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ){
        Icon(
            imageVector = Icons.Default.Android,
            contentDescription = "Android Icon",
            modifier = Modifier.size(40.dp)
        )
    }
    Column(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
        Text(
            text = course.code,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold

        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = course.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal

        )

    }

}

@Composable
fun SimpleCourseList(courses: List<Course>){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ) {
        courses.forEach {
            course ->
            CourseItem(course)
            HorizontalDivider()
        }
    }
}

@Composable
fun LazyCourseList(courses: List<Course>){
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)){
        item {
            Text(
                text = "Courses",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(courses){
            course ->
            CourseItem(course)
            HorizontalDivider()

        }
    }






}