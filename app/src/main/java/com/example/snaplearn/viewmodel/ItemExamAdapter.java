package com.example.snaplearn.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snaplearn.R;
import com.example.snaplearn.model.FlashCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ItemExamAdapter extends RecyclerView.Adapter<ItemExamAdapter.IEHolder> {
    private List<FlashCard> cardList;
    private Random random = new Random();

    public ItemExamAdapter(List<FlashCard> cardList) {
        this.cardList = cardList;
    }
    @NonNull
    @Override
    public ItemExamAdapter.IEHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, parent, false);
        return new ItemExamAdapter.IEHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IEHolder holder, int position) {
        FlashCard card = cardList.get(position);

        // Trộn danh sách các từ
        List<FlashCard> shuffledList = new ArrayList<>(cardList);
        shuffledList.remove(card);
        Collections.shuffle(shuffledList);

        // Chọn ngẫu nhiên một vị trí từ danh sách đã được trộn để sử dụng làm câu trả lời đúng
        int correctAnswerIndex = random.nextInt(4);
        FlashCard correctAnswer = shuffledList.get(correctAnswerIndex);

        // Hiển thị câu hỏi và đáp án đúng
        holder.tv_ask.setText(card.getDefinition());

        // Hiển thị các đáp án (A, B, C, D) theo thứ tự ngẫu nhiên
        setRandomAnswer(holder.rdbtn_A, shuffledList.get(0).getTerm(), correctAnswerIndex);
        setRandomAnswer(holder.rdbtn_B, shuffledList.get(1).getTerm(), correctAnswerIndex);
        setRandomAnswer(holder.rdbtn_C, shuffledList.get(2).getTerm(), correctAnswerIndex);
        setRandomAnswer(holder.rdbtn_D, shuffledList.get(3).getTerm(), correctAnswerIndex);
    }
    private void setRandomAnswer(RadioButton radioButton, String term, int correctAnswerIndex) {
        if (random.nextBoolean()) {
            // Đặt đáp án đúng vào vị trí ngẫu nhiên
            radioButton.setText(correctAnswerIndex == 0 ? term : getRandomTerm());
        } else {
            // Đặt đáp án sai vào vị trí ngẫu nhiên
            radioButton.setText(getRandomTerm());
        }
    }

    private String getRandomTerm() {
        // Lấy một từ ngẫu nhiên từ danh sách
        return cardList.get(random.nextInt(cardList.size())).getTerm();
    }

    public int getItemCount() {
        return cardList.size();
    }
    public static class IEHolder extends RecyclerView.ViewHolder {
        private TextView tv_ask;
        private RadioButton rdbtn_A;
        private RadioButton rdbtn_B;
        private RadioButton rdbtn_C;
        private RadioButton rdbtn_D;
        protected LinearLayout layoutForeGround;

        public IEHolder(View view) {
            super(view);
            tv_ask = view.findViewById(R.id.tv_ask);
            rdbtn_A = view.findViewById(R.id.rdbtn_A);
            rdbtn_B = view.findViewById(R.id.rdbtn_B);
            rdbtn_C = view.findViewById(R.id.rdbtn_C);
            rdbtn_D = view.findViewById(R.id.rdbtn_D);
        }
    }
}
