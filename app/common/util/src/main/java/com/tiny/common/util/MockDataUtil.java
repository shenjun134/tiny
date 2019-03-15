package com.tiny.common.util;

import com.tiny.common.entity.MockData;
import com.tiny.common.entity.MockHead;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockDataUtil {

    public static MockData parseMockData(String file) {
        Reader reader = null;
        MockData mockData = new MockData();
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(file);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(resource.toURI()))));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            int index = 0;
            for (CSVRecord csvRecord : csvParser) {
                int size = csvRecord.size();
                index++;
                if (index == 1) {
                    List<MockHead> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        String value = csvRecord.get(i);
                        MockHead mockHead = new MockHead();
                        mockHead.setName(value);
                        mockHead.setIndex(i);
                        list.add(mockHead);
                    }
                    mockData.setHeadList(list);
                    continue;
                }
                List<String> list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    String value = csvRecord.get(i);
                    list.add(value);
                }
                mockData.getValueList().add(list);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No " + file + " found", e);
        } catch (IOException e) {
            throw new RuntimeException("No " + file + " found", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException("No " + file + " found", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (CollectionUtils.isEmpty(mockData.getHeadList())) {
            return mockData;
        }
        for (int i = 0, len = mockData.getHeadList().size(); i < len; i++) {
            MockHead mockHead = mockData.getHeadList().get(i);
            int max = getMaxSize(mockData, i);
            int headLength = StringUtils.length(mockHead.getName());
            if (max < headLength) {
                max = headLength;
            }
            mockData.getHeadList().get(i).setMaxLenght(max);
        }
        return mockData;
    }

    private static int getMaxSize(MockData mockData, int colIndex) {
        int max = 0;
        for (List<String> row : mockData.getValueList()) {
            String value = row.get(colIndex);
            int length = StringUtils.length(value);
            if (length > max) {
                max = length;
            }
        }
        return max;
    }

    public static List<MockHead> copyShuffle(List<MockHead> headList) {
        List<MockHead> temp = new ArrayList<>(headList);
        Collections.shuffle(temp);
        return temp;
    }
}
