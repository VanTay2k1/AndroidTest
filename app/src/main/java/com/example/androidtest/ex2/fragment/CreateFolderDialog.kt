package com.example.androidtest.ex2.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.androidtest.databinding.DialogCreateFolderBinding
import java.io.File

class CreateFolderDialog : DialogFragment() {

    private lateinit var binding: DialogCreateFolderBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCreateFolderBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext()).setView(binding.root)
            .setPositiveButton("Create") { dialog, id ->
                val folderName = binding.edtNameFolder.text.toString().trim()
                if (folderName.isNotEmpty()) {
                    val fullPath = getRootDirectory().absolutePath + File.separator + folderName
                    if (createFolder(fullPath)) {
                        Log.d("xxxxx", "onCreateDialog: $fullPath")
                        Toast.makeText(
                            requireContext(),
                            "Folder '$folderName' created!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to create folder",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Folder name cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }

        return builder.create()
    }

    private fun createFolder(folderName: String): Boolean {
        val root = getRootDirectory()
        val folder = File(root, folderName)
        return if (!folder.exists()) {
            folder.mkdirs()
        } else {
            false
        }
    }

    private fun getRootDirectory(): File {
        val rootPath = requireContext().getExternalFilesDir(null)?.absolutePath + "/MyLauncherApp"
        val root = File(rootPath)
        if (!root.exists()) {
            if (!root.mkdirs()) {
                Toast.makeText(
                    requireContext(),
                    "Failed to create root directory",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return root
    }
}
