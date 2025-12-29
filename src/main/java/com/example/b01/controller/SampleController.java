package com.example.b01.controller;

import com.example.b01.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {

    @GetMapping("/hello")
    public void hello(Model model){
        log.info("hello...............");
        model.addAttribute("msg","Hello World");
    }

    @GetMapping("/ex/ex1")
    public void ex(Model model){
        List<String> list = Arrays.asList("AAA","BBB","CCC","DDD");
        model.addAttribute("list",list);
    }

//    @GetMapping("/hello1")
//    public void hell01(String name, int age, Model model){
//        model.addAttribute("name",name);
//        model.addAttribute("age",age);
//    }
//
//    @GetMapping("/hello3")
//    public void hello3(@RequestParam(name="types") List<String> types,
//                       int age,
//                       Model model){
//        model.addAttribute("types",types);
//        model.addAttribute("age",age);
//    }

    @GetMapping("/ex/ex2")
    public void ex2(Model model){
        log.info("ex/ex2.......................");

        //1. Stream 방식
        List<String> strList= IntStream.range(1,10)
                .mapToObj(i->"Data"+i)
                .collect(Collectors.toList());

        //2. 전통적 For문 방식
        //객체 생성
        List<String> strList2 = new ArrayList<>();
        for(int i=1; i<10; i++){
            strList2.add("Data"+i);
        }

        model.addAttribute("strList", strList);
        model.addAttribute("strList2", strList2);

        //3. HashMap 컬렉션 생성
        Map<String, Integer> maps = new HashMap<>();
        maps.put("홍길동",80);
        maps.put("박경미",75);
        maps.put("윤요섭",85);

        model.addAttribute("maps",maps);

        //4. SampleDTO
        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.setName("hong");
        sampleDTO.setAge(20);
        sampleDTO.setGender("남자");

        model.addAttribute("sampleDTO",sampleDTO);

    }

    @GetMapping("/ex/ex3")
    public void ex3(Model model){
        model.addAttribute("arr",new String[]{"AAA","BBB","CCC"});
    }

}
