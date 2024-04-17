package com.jt.www.model.reps;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Data
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericListResponse<T> extends GenericResponse {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("list")
    private List<T> list;

    public static <T> ResponseEntity<GenericListResponse<T>> listNoCount(List<T> arr) {
        GenericListResponse<T> resp = new GenericListResponse<T>();
        resp.setCode(CODE_OK);
        if (arr == null) {
            resp.setTotal(0);
        } else {
            resp.setTotal(arr.size());
        }
        resp.setList(arr);
        return ResponseEntity.ok(resp);
    }

    public static <T> ResponseEntity<GenericListResponse<T>> listNoCount(String rc, String msg, List<T> arr) {
        GenericListResponse<T> resp = new GenericListResponse<T>();
        resp.setCode(rc);
        resp.setMsg(msg);
        if (arr == null) {
            resp.setTotal(0);
        } else {
            resp.setTotal(arr.size());
        }
        resp.setList(arr);
        return ResponseEntity.ok(resp);
    }

    public static ResponseEntity<?> listAndCount(List<?> arr, Integer tc) {
        return listAndCount(arr,  new Long(tc));
    }

    public static <T> ResponseEntity<GenericListResponse<T>> listAndCount(List<T> arr, Long tc) {
        GenericListResponse<T> resp = new GenericListResponse<T>();
        resp.setCode(CODE_OK);
        if (arr == null) {
            resp.setTotal(0);
        } else {
            resp.setList(arr);
            resp.setTotal(Integer.parseInt(Long.toString(tc)));
        }
        return ResponseEntity.ok(resp);
    }

}
