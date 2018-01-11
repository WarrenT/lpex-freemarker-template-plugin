using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace TemplateBuilder
{
  public class UTF8StringWriter : StringWriter
  {
    public override Encoding Encoding {
      get { return Encoding.UTF8; }
    }
  }
}
