package org.eclipse.daanse.xmla.ws.jakarta.basic;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;

import java.util.List;

import org.eclipse.daanse.xmla.api.engine300_300.XEvent;
import org.eclipse.daanse.xmla.api.xmla.AndOrType;
import org.eclipse.daanse.xmla.api.xmla.AndOrTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.BoolBinop;
import org.eclipse.daanse.xmla.api.xmla.Event;
import org.eclipse.daanse.xmla.api.xmla.EventColumnID;
import org.eclipse.daanse.xmla.api.xmla.EventSession;
import org.eclipse.daanse.xmla.api.xmla.EventType;
import org.eclipse.daanse.xmla.api.xmla.NotType;
import org.eclipse.daanse.xmla.api.xmla.PartitionModes;
import org.eclipse.daanse.xmla.api.xmla.RetentionModes;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.TraceFilter;
import org.eclipse.daanse.xmla.model.record.engine300_300.XEventR;
import org.eclipse.daanse.xmla.model.record.xmla.AndOrTypeR;
import org.eclipse.daanse.xmla.model.record.xmla.BoolBinopR;
import org.eclipse.daanse.xmla.model.record.xmla.EventColumnIDR;
import org.eclipse.daanse.xmla.model.record.xmla.EventR;
import org.eclipse.daanse.xmla.model.record.xmla.EventSessionR;
import org.eclipse.daanse.xmla.model.record.xmla.EventTypeR;
import org.eclipse.daanse.xmla.model.record.xmla.NotTypeR;
import org.eclipse.daanse.xmla.model.record.xmla.TraceFilterR;
import org.eclipse.daanse.xmla.model.record.xmla.TraceR;

import jakarta.xml.bind.JAXBElement;

public class TraceConvertor {

	private TraceConvertor() {
	}
    public static Trace convertTrace(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Trace trace) {
        if (trace != null) {
            return new TraceR(trace.getName(),
                trace.getID(),
                convertToInstant(trace.getCreatedTimestamp()),
                convertToInstant(trace.getLastSchemaUpdate()),
                trace.getDescription(),
                convertAnnotationList(trace.getAnnotations() == null ? null : trace.getAnnotations()),
                trace.getLogFileName(),
                trace.isLogFileAppend(),
                trace.getLogFileSize(),
                trace.isAudit(),
                trace.isLogFileRollover(),
                trace.isAutoRestart(),
                convertToInstant(trace.getStopTime()),
                convertTraceFilter(trace.getFilter()),
                convertEventType(trace.getEventType()));
        }
        return null;
    }

    private static TraceFilter convertTraceFilter(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.TraceFilter filter) {
        if (filter != null) {
            return new TraceFilterR(convertNotType(filter.getNot()),
                convertAndOrType(filter.getOr()),
                convertAndOrType(filter.getAnd()),
                convertBoolBinop(filter.getEqual()),
                convertBoolBinop(filter.getNotEqual()),
                convertBoolBinop(filter.getLess()),
                convertBoolBinop(filter.getLessOrEqual()),
                convertBoolBinop(filter.getGreater()),
                convertBoolBinop(filter.getGreaterOrEqual()),
                convertBoolBinop(filter.getLike()),
                convertBoolBinop(filter.getNotLike()));
        }
        return null;
    }

    private static BoolBinop convertBoolBinop(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.BoolBinop equal) {
        if (equal != null) {
            return new BoolBinopR(equal.getColumnID(),
                equal.getValue());
        }
        return null;
    }

    private static AndOrType convertAndOrType(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AndOrType or) {
        if (or != null) {
            return new AndOrTypeR(convertAndOrTypeEnumList(or.getNotOrOrOrAnd()));
        }
        return null;
    }

    private static List<AndOrTypeEnum> convertAndOrTypeEnumList(List<JAXBElement<?>> list) {
        if (list != null) {
            return list.stream().map(TraceConvertor::convertAndOrTypeEnum).toList();
        }
        return List.of();

    }

    private static AndOrTypeEnum convertAndOrTypeEnum(JAXBElement<?> jaxbElement) {
        if (jaxbElement != null) {
            AndOrTypeEnum.valueOf(jaxbElement.getName().getPrefix());
            //TODO check
        }
        return null;
    }

    private static NotType convertNotType(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.NotType not) {
        if (not != null) {
            return new NotTypeR(convertNotType(not.getNot()),
                convertAndOrType(not.getOr()),
                convertAndOrType(not.getAnd()),
                convertBoolBinop(not.getEqual()),
                convertBoolBinop(not.getNotEqual()),
                convertBoolBinop(not.getLess()),
                convertBoolBinop(not.getLessOrEqual()),
                convertBoolBinop(not.getGreater()),
                convertBoolBinop(not.getGreaterOrEqual()),
                convertBoolBinop(not.getLike()),
                convertBoolBinop(not.getNotLike()));
        }
        return null;
    }

    private static EventType convertEventType(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.EventType eventType) {
        if (eventType != null) {
            return new EventTypeR(convertEventList(eventType.getEvents()),
                convertXEvent(eventType.getXEvent()));
        }
        return null;
    }

    private static List<Event> convertEventList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Event> list) {
        if (list != null) {
            return list.stream().map(TraceConvertor::convertEvent).toList();
        }
        return List.of();
    }

    private static Event convertEvent(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Event event) {
        if (event != null) {
            return new EventR(event.getEventID(),
                convertEventColumnID(event.getColumns()));
        }
        return null;
    }

    private static EventColumnID convertEventColumnID(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.EventColumnID columns) {
        if (columns != null) {
            return new EventColumnIDR(columns.getColumnID());
        }
        return null;
    }

    private static XEvent convertXEvent(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300_300.XEvent xEvent) {
        if (xEvent != null) {
            return new XEventR(convertEventSession(xEvent.getEventSession()));
        }
        return null;
    }

    private static EventSession convertEventSession(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.EventSession eventSession) {
        if (eventSession != null) {
            return new EventSessionR(eventSession.getTemplateCategory(),
                eventSession.getTemplateName(),
                eventSession.getTemplateDescription(),
                null,// eventSession.getEvent(),
               null,// eventSession.getTarget(),
                eventSession.getName(),
                eventSession.getMaxMemory(),
                convertRetentionModes(eventSession.getEventRetentionMode()),
                eventSession.getDispatchLatency(),
                eventSession.getMaxEventSize(),
                convertPartitionModes(eventSession.getMemoryPartitionMode()),
                eventSession.getTrackCausality());
        }
        return null;
    }

    private static PartitionModes convertPartitionModes(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.PartitionModes memoryPartitionMode) {
        if (memoryPartitionMode != null) {
            return PartitionModes.fromValue(memoryPartitionMode.value());
        }
        return null;
    }

    private static RetentionModes convertRetentionModes(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.RetentionModes eventRetentionMode) {
        if (eventRetentionMode != null) {
            return RetentionModes.fromValue(eventRetentionMode.value());
        }
        return null;

    }

    public static List<Trace> convertTraceList(List <org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Trace> list) {
        if (list != null) {
            return list.stream().map(TraceConvertor::convertTrace).toList();
        }
        return List.of();
    }
}
