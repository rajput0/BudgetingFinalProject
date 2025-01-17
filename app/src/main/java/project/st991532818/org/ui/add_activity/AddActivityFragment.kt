package project.st991532818.org.ui.add_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentAddactivityBinding

class AddActivityFragment : Fragment() {

    private lateinit var addActivityViewModel: AddActivityViewModel
    private var _binding: FragmentAddactivityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addActivityViewModel =
            ViewModelProvider(this).get(AddActivityViewModel::class.java)

        _binding = FragmentAddactivityBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val monthTextView: TextView = binding.textMonth
        addActivityViewModel.text.observe(viewLifecycleOwner, Observer {
            monthTextView.text = it
        })

        val spinnerMonth = binding.monthSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.months_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerMonth.adapter = adapter
            }
        }

        val spinnerYear = binding.yearSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.year_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerYear.adapter = adapter
            }
        }

        val spinnerActivityCategory = binding.editExpenseCategory
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.category_array,
                // todo change this array to dynamic array (user can add manage its items)
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerActivityCategory.adapter = adapter
            }
        }

        val spinnerActivityType = binding.activitytypeSpinner
        context?.let{
            ArrayAdapter.createFromResource(
                it,
                R.array.activity_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerActivityType.adapter = adapter
            }
        }

        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    addActivityViewModel.updateText(parent.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }

        binding.btnAddexpense.setOnClickListener {
            addNewItem()
        }

    }
    // onViewCreated Ends here

    private fun addNewItem() {
        if (isEntryValid()) {
            addActivityViewModel.addNewExpense(
                binding.editExpenseAmount.text.toString(),
                binding.editExpenseCategory.selectedItem.toString(),
            )
            Toast.makeText(context, "Expense Added. Expense list is updated!!", Toast.LENGTH_SHORT)
//            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
//            findNavController().navigate(action)
        }
    }

    private fun isEntryValid(): Boolean {
        return addActivityViewModel.isEntryValid(
            binding.editExpenseAmount.text.toString(),
            binding.editExpenseCategory.selectedItem.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

