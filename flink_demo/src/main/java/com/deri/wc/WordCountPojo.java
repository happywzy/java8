package com.deri.wc;

import com.deri.wc.util.WordCountData;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.util.Collector;


@SuppressWarnings("serial")
public class WordCountPojo {

	public static class Word {
		private String word;
		private int frequency;
		public Word() {}

		public Word(String word, int i) {
			this.word = word;
			this.frequency = i;
		}
		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public int getFrequency() {
			return frequency;
		}

		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		@Override
		public String toString() {
			return "Word=" + word + " freq=" + frequency;
		}
	}

	public static void main(String[] args) throws Exception {

		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		DataSet<String> text = WordCountData.getDefaultTextLineDataSet(env);

		DataSet<Word> counts =
			text.flatMap(new Tokenizer())
			.groupBy("word")
			.reduce(new ReduceFunction<Word>() {
				@Override
				public Word reduce(Word value1, Word value2) throws Exception {
					return new Word(value1.word, value1.frequency + value2.frequency);
				}
			});

		counts.print();
		env.execute("WordCountPojo");
	}

	public static final class Tokenizer implements FlatMapFunction<String, Word> {

		@Override
		public void flatMap(String value, Collector<Word> out) {
			String[] tokens = value.toLowerCase().split("\\W+");
			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Word(token, 1));
				}
			}
		}
	}

}
