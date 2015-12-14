package javelin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

class Builtin {
	public static Object coerceNumberType(List<Object> args) {
		Object r = Integer.TYPE;
		for (Object x : args) {
			if (x instanceof Double) {
				return Double.TYPE;
			} else if (x instanceof Long) {
				r = Long.TYPE;
			}
		}
		return r;
	}
	
	static class _plus implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			int len = args.size();
			if (len <= 0)
				return 0;
			Object type = coerceNumberType(args);
			Object first = args.get(0);
			if (type == Integer.TYPE) {
				int acc = Core.intValue(first);
				for (int i = 1; i < len; i++) {
					acc += Core.intValue(args.get(i));
				}
				return acc;
			} else if (type == Long.TYPE) {
				long acc = Core.longValue(first);
				for (int i = 1; i < len; i++) {
					acc += Core.longValue(args.get(i));
				}
				return acc;
			} else {
				double acc = Core.doubleValue(first);
				for (int i = 1; i < len; i++) {
					acc += Core.doubleValue(args.get(i));
				}
				return acc;
			}
		}
	}

	static class _minus implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			int len = args.size();
			if (len <= 0)
				return 0;
			Object type = coerceNumberType(args);
			Object first = args.get(0);
			if (type == Integer.TYPE) {
				int acc = Core.intValue(first);
				if (len == 1) return -acc;
				for (int i = 1; i < len; i++) {
					acc -= Core.intValue(args.get(i));
				}
				return acc;
			} else if (type == Long.TYPE) {
				long acc = Core.longValue(first);
				if (len == 1) return -acc;
				for (int i = 1; i < len; i++) {
					acc -= Core.longValue(args.get(i));
				}
				return acc;
			} else {
				double acc = Core.doubleValue(first);
				if (len == 1) return -acc;
				for (int i = 1; i < len; i++) {
					acc -= Core.doubleValue(args.get(i));
				}
				return acc;
			}
		}
	}

	static class _star implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			int len = args.size();
			if (len <= 0)
				return 1;
			Object type = coerceNumberType(args);
			Object first = args.get(0);
			if (type == Integer.TYPE) {
				int acc = Core.intValue(first);
				for (int i = 1; i < len; i++) {
					acc *= Core.intValue(args.get(i));
				}
				return acc;
			} else if (type == Long.TYPE) {
				long acc = Core.longValue(first);
				for (int i = 1; i < len; i++) {
					acc *= Core.longValue(args.get(i));
				}
				return acc;
			} else {
				double acc = Core.doubleValue(first);
				for (int i = 1; i < len; i++) {
					acc *= Core.doubleValue(args.get(i));
				}
				return acc;
			}
		}
	}

	static class _slash implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			int len = args.size();
			if (len <= 0)
				return 1;
			Object type = coerceNumberType(args);
			Object first = args.get(0);
			if (type == Integer.TYPE) {
				int acc = Core.intValue(first);
				if (len == 1) return 1 / acc;
				for (int i = 1; i < len; i++) {
					acc /= Core.intValue(args.get(i));
				}
				return acc;
			} else if (type == Long.TYPE) {
				long acc = Core.longValue(first);
				if (len == 1) return 1 / acc;
				for (int i = 1; i < len; i++) {
					acc /= Core.longValue(args.get(i));
				}
				return acc;
			} else {
				double acc = Core.doubleValue(first);
				if (len == 1) return 1 / acc;
				for (int i = 1; i < len; i++) {
					acc /= Core.doubleValue(args.get(i));
				}
				return acc;
			}
		}
	}

	static class mod implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object first = args.get(0);
			Object second = args.get(1);
			Object type = coerceNumberType(args);
			if (type == Integer.TYPE) {
				return Core.intValue(first) % Core.intValue(second);
			} else if (type == Long.TYPE) {
				return Core.longValue(first) % Core.longValue(second);
			} else {
				return Core.doubleValue(first) % Core.doubleValue(second);
			}
		}
	}

	static class _eq implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object v1 = args.get(0);
			if (v1 == null) {
				for (int i = 1; i < args.size(); i++) {
					Object v2 = args.get(i);
					if (v2 != null) return false;
				}
			}
			else {
				for (int i = 1; i < args.size(); i++) {
					Object v2 = args.get(i);
					if (!v1.equals(v2)) return false;
				}
			}
			return true;
		}
	}

	static class _eq_eq implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object first = args.get(0);
			double firstv = Core.doubleValue(first);
			for (int i = 1; i < args.size(); i++) {
				if (Core.doubleValue(args.get(i)) != firstv) {
					return false;
				}
			}
			return true;
		}
	}

	static class Not_eq implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object v1 = args.get(0);
			if (v1 == null) {
				for (int i = 1; i < args.size(); i++) {
					Object v2 = args.get(i);
					if (v2 != null) return true;
				}
			}
			else {
				for (int i = 1; i < args.size(); i++) {
					Object v2 = args.get(i);
					if (!v1.equals(v2)) return true;
				}
			}
			return false;
		}
	}

	static class _lt implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			for (int i = 0; i < args.size() - 1; i++) {
				Object first = args.get(i);
				Object second = args.get(i + 1);
				if (!(Core.doubleValue(first) < Core.doubleValue(second))) return false;
			}
			return true;
		}
	}

	static class _gt implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			for (int i = 0; i < args.size() - 1; i++) {
				Object first = args.get(i);
				Object second = args.get(i + 1);
				if (!(Core.doubleValue(first) > Core.doubleValue(second))) return false;
			}
			return true;
		}
	}

	static class _lt_eq implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			for (int i = 0; i < args.size() - 1; i++) {
				Object first = args.get(i);
				Object second = args.get(i + 1);
				if (!(Core.doubleValue(first) <= Core.doubleValue(second))) return false;
			}
			return true;
		}
	}

	static class _gt_eq implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			for (int i = 0; i < args.size() - 1; i++) {
				Object first = args.get(i);
				Object second = args.get(i + 1);
				if (!(Core.doubleValue(first) >= Core.doubleValue(second))) return false;
			}
			return true;
		}
	}

	static class not implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return !Core.booleanValue(args.get(0));
		}
	}

	static class read_string implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return Core.parse(new StringReader((String) args.get(0)));
		}
	}

	static class type implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return Core.type(args.get(0));
		}
	}

	static class eval implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return Core.preprocessEval(args.get(0), Core.globalEnv);
		}
	}

	static class fold implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object f = args.get(0);
			ArrayList<Object> lst = Core.arrayListValue(args.get(1));
			Object acc = lst.get(0);
			ArrayList<Object> args2 = new ArrayList<Object>(); // (ITEM1 ITEM2)
			args2.add(null); // first argument
			args2.add(null); // second argument
			for (int i = 1; i < lst.size(); i++) {
				args2.set(0, acc);
				args2.set(1, lst.get(i));
				acc = Core.apply(f, args2);
			}
			return acc;
		}
	}

	static class map implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object f = args.get(0);
			ArrayList<Object> lst = Core.arrayListValue(args.get(1));
			ArrayList<Object> acc = new ArrayList<Object>();
			for (int i = 0; i < lst.size(); i++) {
				ArrayList<Object> args2 = new ArrayList<Object>();
				args2.add(lst.get(i));
				acc.add(Core.apply(f, args2));
			}
			return acc;
		}
	}

	static class apply implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return Core.apply(args.get(0), Core.arrayListValue(args.get(1)));
		}
	}

	static class filter implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			Object f = args.get(0);
			ArrayList<Object> lst = Core.arrayListValue(args.get(1));
			ArrayList<Object> acc = new ArrayList<Object>();
			for (int i = 0; i < lst.size(); i++) {
				ArrayList<Object> args2 = new ArrayList<Object>();
				Object item = lst.get(i);
				args2.add(item);
				Object ret = Core.apply(f, args2);
				if (Core.booleanValue(ret)) acc.add(item);
			}
			return acc;
		}
	}

	static class pr implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			for (int i = 0; i < args.size(); i++) {
				if (i != 0) System.out.print(" ");
				System.out.print(args.get(i));
			}
			return null;
		}
	}

	static final pr pr1 = new pr();

	static class prn implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			pr1.invoke(args);
			System.out.println();
			return null;
		}
	}

	static class read_line implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				return br.readLine();
			} catch (IOException e) {
				return null;
			}
		}
	}

	// (slurp filename [encoding]) default encoding: UTF-8
	static class slurp implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			String filename = Core.toString(args.get(0));
			String charset = args.size() >= 2 ? args.get(1).toString() : "UTF-8";
			return Core.slurp(filename, charset);
		}
	}

	static class spit implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			String filename = Core.toString(args.get(0));
			String str = Core.toString(args.get(1));
			return Core.spit(filename, str);
		}
	}

	static class list implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return args;
		}
	}

	static class str implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			StringBuilder sb = new StringBuilder();
			for (Object x : args) {
				if (x != null) sb.append(x.toString());
			}
			return sb.toString();
		}
	}

	static class symbol implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return new javelin.Symbol(Core.toString(args.get(0)));
		}
	}

	// (macroexpand X)
	static class macroexpand implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			return Core.macroexpand(args.get(0));
		}
	}
	
	// (read [Reader])
	static class read implements IFn {
		public Object invoke(ArrayList<Object> args) throws Throwable {
			switch (args.size()) {
			case 0: return Core.parse(Core.defaultReader);
			case 1: return Core.parse((Reader) args.get(1));
			default: throw new IllegalArgumentException();
			}
		}
	}
}
