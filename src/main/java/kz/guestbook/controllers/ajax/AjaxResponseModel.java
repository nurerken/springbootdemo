package kz.guestbook.controllers.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxResponseModel {
    /*
	/**********************************************************************
	/* Fields
	/**********************************************************************
	*/
    private String resultCode;
    private Object result;
    private List<String> errorsGlobal;
    private Map<String, String> errorsFields;

    /*
	/**********************************************************************
	/* Constants
	/**********************************************************************
	*/
    public final static String RESULT_CODE_OK = "OK";
    public final static String RESULT_CODE_ERROR = "ERROR";

    /*
	/**********************************************************************
    /* Constructors
    /**********************************************************************
    */
    public AjaxResponseModel() {
        errorsGlobal = new ArrayList<String>();
        errorsFields = new HashMap<String, String>();
    }

    public AjaxResponseModel(String resultCode, Object result) {
        this();
        this.resultCode = resultCode;
        this.result = result;
    }

    /*
	/**********************************************************************
	/* Getters & Setters
	/**********************************************************************
	*/
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }

    public List<String> getErrorsGlobal() {
        return errorsGlobal;
    }
    public void setErrorsGlobal(List<String> errorsGlobal) {
        this.errorsGlobal = errorsGlobal;
    }

    public Map<String, String> getErrorsFields() {
        return errorsFields;
    }
    public void setErrorsFields(Map<String, String> errorsFields) {
        this.errorsFields = errorsFields;
    }


    /*
	/**********************************************************************
	/* Additional methods
	/**********************************************************************
	*/
    public AjaxResponseModel setResultCodeOk() {
        this.setResultCode(RESULT_CODE_OK);
        return this;
    }

    public AjaxResponseModel setResultCodeOk(Object result) {
        this.setResultCodeOk();
        this.setResult(result);
        return this;
    }

    public AjaxResponseModel setResultCodeError() {
        this.setResultCode(RESULT_CODE_ERROR);
        return this;
    }

    public AjaxResponseModel setResultCodeError(String globalError) {
        this.setResultCode(RESULT_CODE_ERROR);

        if(globalError!=null) {
            this.getErrorsGlobal().add(globalError);
        }

        return this;
    }
}
