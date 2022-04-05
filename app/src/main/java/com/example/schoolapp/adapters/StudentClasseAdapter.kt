package com.example.schoolapp.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.*
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.AttendanceViewModel
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel
import com.example.schoolapp.viewModels.ClasseViewModel

class StudentClasseAdapter(private val onClickListener: OnClickListener, private val classeViewModel: AttendanceViewModel, date: String): ListAdapter<StudentWithclass, StudentClasseAdapter.StudentClasseViewHolder>(StudentComparator()) {
//    private val classeStudentsViewModel: ClasseStudentViewModel by  {
//        ClasseStudentModelFactory((activity as MainApp).repositoryClasseStudent)
//    }
    private val classeViewModel0: AttendanceViewModel = classeViewModel
    private val date0: String = date
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentClasseAdapter.StudentClasseViewHolder {
        return StudentClasseAdapter.StudentClasseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentClasseAdapter.StudentClasseViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, classeViewModel0, date0)
    }
    class OnClickListener(val clickListener: (student: StudentWithclass) -> Unit) {
        fun onClick(student: StudentWithclass) = clickListener(student)
    }
    class StudentClasseViewHolder(itemView: View, ) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        private val button: ImageButton = itemView.findViewById(R.id.person)
        fun bind(student: StudentWithclass?, onClickListener: OnClickListener, classeViewModel0: AttendanceViewModel, date0: String) {
            wordItemView.text = student!!.student.name + " " + student!!.student.prenom
            card.setOnClickListener { view ->
//                goToStudent(student.sid, view)
                onClickListener.onClick(student)
            }
            var attendance: Attendance = classeViewModel0.searchByDate(date0, student!!.student.sid, student!!.classRoom_Student.class_room_id)
            if (attendance != null && attendance.status == 0) {
                button.setBackgroundColor(Color.RED)
//                student.classRoom_Student.attendance = 1
            }
            else if (attendance != null && attendance.status == 1) {
                button.setBackgroundColor(Color.GREEN)
            }
            button.setOnClickListener { view ->
                if (attendance == null) {
                    button.setBackgroundColor(Color.RED)
                    classeViewModel0.insert(Attendance(0, student.classRoom_Student.student_id,
                        student.classRoom_Student.class_room_id, 0, date0))
                    attendance = classeViewModel0.searchByDate(date0, student!!.student.sid, student!!.classRoom_Student.class_room_id)

                }
                else if (attendance.status == 0) {
                    button.setBackgroundColor(Color.GREEN)
                    classeViewModel0.update(Attendance(attendance.aid, student.classRoom_Student.student_id,
                        student.classRoom_Student.class_room_id, 1, date0))
                    attendance = Attendance(attendance.aid, student.classRoom_Student.student_id,
                        student.classRoom_Student.class_room_id, 1, date0)
                }
                else if (attendance.status == 1) {
                    button.setBackgroundColor(Color.RED)
                    classeViewModel0.update(Attendance(attendance.aid, student.classRoom_Student.student_id,
                        student.classRoom_Student.class_room_id, 0, date0))
                    attendance = Attendance(attendance.aid, student.classRoom_Student.student_id,
                        student.classRoom_Student.class_room_id, 0, date0)
                }

            }
        }

        companion object {
            fun create(parent: ViewGroup): StudentClasseAdapter.StudentClasseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_attendence_view, parent, false)
                return StudentClasseAdapter.StudentClasseViewHolder(view)
            }
        }
        private fun goToStudent(id: Int, v: View){
            val student00 = LinkedHashMap<String, String>()
            Log.d("TEST TEST", student00["nom"].toString())
            val intent = Intent(v.context, AddStudentPage::class.java)
            intent.putExtra("id", id);
            v.context.startActivity(intent)
        }
    }

    class StudentComparator : DiffUtil.ItemCallback<StudentWithclass>() {
        override fun areItemsTheSame(oldItem: StudentWithclass, newItem: StudentWithclass): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: StudentWithclass, newItem: StudentWithclass): Boolean {
            return oldItem.student.sid == newItem.student.sid
        }
    }
}